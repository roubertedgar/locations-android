// Generated by Dagger (https://dagger.dev).
package com.hotmart.locations.core.injection;

import android.content.Context;
import com.hotmart.locations.core.http.HttpManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerCoreComponent implements CoreComponent {
  private final Context context;

  private final DaggerCoreComponent coreComponent = this;

  private Provider<OkHttpClient> providesHttpClient$core_productionProvider;

  private Provider<HttpManager> providesHttpManager$core_productionProvider;

  private DaggerCoreComponent(CoreModule coreModuleParam, Context contextParam) {
    this.context = contextParam;
    initialize(coreModuleParam, contextParam);

  }

  public static CoreComponent.Factory factory() {
    return new Factory();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final CoreModule coreModuleParam, final Context contextParam) {
    this.providesHttpClient$core_productionProvider = DoubleCheck.provider(CoreModule_ProvidesHttpClient$core_productionFactory.create(coreModuleParam));
    this.providesHttpManager$core_productionProvider = DoubleCheck.provider(CoreModule_ProvidesHttpManager$core_productionFactory.create(coreModuleParam, providesHttpClient$core_productionProvider));
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public HttpManager getHttpManager() {
    return providesHttpManager$core_productionProvider.get();
  }

  @Override
  public OkHttpClient getOkHttpClient() {
    return providesHttpClient$core_productionProvider.get();
  }

  private static final class Factory implements CoreComponent.Factory {
    @Override
    public CoreComponent create(Context context) {
      Preconditions.checkNotNull(context);
      return new DaggerCoreComponent(new CoreModule(), context);
    }
  }
}
