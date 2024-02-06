package biped.works.automation

import retrofit2.Call

class Unauthorized(override val message: String?) : Exception()
class ResourceNotFound(override val message: String?) : Exception()
class Conflict(override val message: String?) : Exception()
class ValidationFailed(override val message: String?) : Exception()

inline fun <reified T : Any> executeCall(call: Call<T>): T? {
    val response = call.execute()
    val errorMessage = response.errorBody()?.byteString().toString()
    when (response.code()) {
        in 200..299 -> return response.body()

        401 -> throw Unauthorized(errorMessage)
        404 -> throw ResourceNotFound(errorMessage)
        409 -> throw Conflict(errorMessage)
        422 -> throw ValidationFailed(errorMessage)
        else -> throw Exception(errorMessage)
    }
}

class GitHubRepository(private val gitHubApi: GitHubApi) {

    fun getVersionFile(branch: String): FileRequest {
        return executeCall(gitHubApi.readVersionsVile(branch)) ?: throw Exception("No file found")
    }

    fun updateVersionFile(file: FileUpdate) {
        executeCall(gitHubApi.updateVersionFile(file))
    }

    fun getBranch(branch: String): Object {
        return executeCall(gitHubApi.listRefs())
            ?.first { it.ref == "refs/heads/$branch" }
            ?.objectt ?: throw Exception("No branch found with $branch name")
    }

    fun createReference(reference: ReferenceRequest): ReferenceResponse {
        return executeCall(gitHubApi.createReference(reference)) ?: throw Exception("Unable to create reference")
    }

    fun getCurrentStable(): ReleaseResponse {
        return executeCall(gitHubApi.getLastReleases())?.first { it.isPreRelease.not() }
            ?: throw Exception("Unable to find a release")
    }

    fun getLatestRelease(): ReleaseResponse {
        return executeCall(gitHubApi.getLastReleases(perPage = 1))?.first()
            ?: throw Exception("Unable to find the last release")
    }

    fun updateRelease(id: String, release: ReleaseRequest): ReleaseResponse? {
        return executeCall(gitHubApi.updateRelease(id, release))
            ?: throw Exception("Unable to update release ${release.name}")
    }

    fun createPullRequest(pullRequest: PullRequest) {
        try {
            executeCall(gitHubApi.createPullRequest(pullRequest))
        } catch (error: Throwable) {
            if (error is ValidationFailed) println(
                "Unable to open pull request, no diffs found for ${pullRequest.head} -> ${pullRequest.base}"
            )
        }
    }

    fun createRelease(release: ReleaseRequest) {
        executeCall(gitHubApi.createRelease(release))
    }
}