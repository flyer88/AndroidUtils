Utils
=============
Usage:

+ first `Utils.initialized(Context context)` to get Application Context

```
	public class UtilsApp extends Application{

   	 	@Override
   	 	public void onCreate() {
     	   		super.onCreate();
       	 	Utils.initialized(this);
    		}	
	}
```

+ second just use like `ScreenUtils.getScreenHeight();`



