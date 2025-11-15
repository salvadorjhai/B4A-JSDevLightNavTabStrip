package b4a.example.navtabstrip;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example.navtabstrip", "b4a.example.navtabstrip.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example.navtabstrip", "b4a.example.navtabstrip.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.navtabstrip.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public com.salvadorjhai.widgets.JSDevLightNavTabStrip _tab1 = null;
public com.salvadorjhai.widgets.JSDevLightNavTabStrip _tab2 = null;
public de.amberhome.viewpager.AHViewPager _ahviewpager1 = null;
public de.amberhome.viewpager.AHViewPager _ahviewpager2 = null;
public b4a.example.navtabstrip.starter _v5 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
com.salvadorjhai.widgets.JSDevLightNavTabStrip _tab3 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl1 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl2 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl3 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl4 = null;
de.amberhome.viewpager.AHPageContainer _container = null;
 //BA.debugLineNum = 30;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 32;BA.debugLine="Activity.LoadLayout(\"1\")";
mostCurrent._activity.LoadLayout("1",mostCurrent.activityBA);
 //BA.debugLineNum = 35;BA.debugLine="tab1.Titles = Array As String(\"Videos\", \"Images\",";
mostCurrent._tab1.setTitles(new String[]{"Videos","Images","Song"});
 //BA.debugLineNum = 36;BA.debugLine="tab1.ActiveColor = 0xFFE91E63";
mostCurrent._tab1.setActiveColor((int) (0xffe91e63));
 //BA.debugLineNum = 37;BA.debugLine="tab1.StripColor = 0xFFE91E63";
mostCurrent._tab1.setStripColor((int) (0xffe91e63));
 //BA.debugLineNum = 38;BA.debugLine="tab1.TabIndex = 0";
mostCurrent._tab1.setTabIndex((int) (0));
 //BA.debugLineNum = 41;BA.debugLine="tab2.Titles = Array As String(\"Eat\", \"Sleep\", \"Co";
mostCurrent._tab2.setTitles(new String[]{"Eat","Sleep","Conquer","Repeat"});
 //BA.debugLineNum = 42;BA.debugLine="tab2.TitleSize = 13dip";
mostCurrent._tab2.setTitleSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
 //BA.debugLineNum = 43;BA.debugLine="tab2.TabIndex = 4";
mostCurrent._tab2.setTabIndex((int) (4));
 //BA.debugLineNum = 46;BA.debugLine="Dim tab3 As JSDevLightNavTabStrip";
_tab3 = new com.salvadorjhai.widgets.JSDevLightNavTabStrip();
 //BA.debugLineNum = 47;BA.debugLine="tab3.Initialize(\"tab3\")";
_tab3.Initialize(mostCurrent.activityBA,"tab3");
 //BA.debugLineNum = 48;BA.debugLine="Activity.AddView(tab3, 0dip, 100%y - 56dip, 100%x";
mostCurrent._activity.AddView((android.view.View)(_tab3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (56))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (56)));
 //BA.debugLineNum = 49;BA.debugLine="tab3.Color = Colors.White";
_tab3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 50;BA.debugLine="tab3.Titles = Array As String(\"Home\", \"Think\", \"D";
_tab3.setTitles(new String[]{"Home","Think","Design","Animate"});
 //BA.debugLineNum = 51;BA.debugLine="tab3.ActiveColor = 0xFF8BC34A";
_tab3.setActiveColor((int) (0xff8bc34a));
 //BA.debugLineNum = 52;BA.debugLine="tab3.InactiveColor = Colors.LightGray";
_tab3.setInactiveColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 53;BA.debugLine="tab3.StripType = tab3.STRIP_TYPE_LINE";
_tab3.setStripType(_tab3.STRIP_TYPE_LINE);
 //BA.debugLineNum = 54;BA.debugLine="tab3.StripGravity = tab3.STRIP_GRAVITY_BOTTOM";
_tab3.setStripGravity(_tab3.STRIP_GRAVITY_BOTTOM);
 //BA.debugLineNum = 55;BA.debugLine="tab3.StripColor = 0xFF8BC34A";
_tab3.setStripColor((int) (0xff8bc34a));
 //BA.debugLineNum = 58;BA.debugLine="Dim pnl1 As Panel";
_pnl1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="pnl1.Initialize(\"\")";
_pnl1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 60;BA.debugLine="pnl1.Color = Colors.Green";
_pnl1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 //BA.debugLineNum = 62;BA.debugLine="Dim pnl2 As Panel";
_pnl2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="pnl2.Initialize(\"\")";
_pnl2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 64;BA.debugLine="pnl2.Color = Colors.Yellow";
_pnl2.setColor(anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 66;BA.debugLine="Dim pnl3 As Panel";
_pnl3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="pnl3.Initialize(\"\")";
_pnl3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 68;BA.debugLine="pnl3.Color = Colors.Blue";
_pnl3.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 70;BA.debugLine="Dim pnl4 As Panel";
_pnl4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="pnl4.Initialize(\"\")";
_pnl4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 72;BA.debugLine="pnl4.Color = Colors.Magenta";
_pnl4.setColor(anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 74;BA.debugLine="Dim container As AHPageContainer";
_container = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 75;BA.debugLine="container.Initialize";
_container.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 76;BA.debugLine="container.AddPage(pnl1, \"Home\")";
_container.AddPage((android.view.View)(_pnl1.getObject()),"Home");
 //BA.debugLineNum = 77;BA.debugLine="container.AddPage(pnl2, \"Think\")";
_container.AddPage((android.view.View)(_pnl2.getObject()),"Think");
 //BA.debugLineNum = 78;BA.debugLine="container.AddPage(pnl3, \"Design\")";
_container.AddPage((android.view.View)(_pnl3.getObject()),"Design");
 //BA.debugLineNum = 79;BA.debugLine="container.AddPage(pnl4, \"Animate\")";
_container.AddPage((android.view.View)(_pnl4.getObject()),"Animate");
 //BA.debugLineNum = 81;BA.debugLine="AHViewPager1.PageContainer = container";
mostCurrent._ahviewpager1.setPageContainer(_container);
 //BA.debugLineNum = 82;BA.debugLine="tab2.ViewPager = AHViewPager1";
mostCurrent._tab2.setViewPager((Object)(mostCurrent._ahviewpager1.getObject()));
 //BA.debugLineNum = 83;BA.debugLine="tab2.TabIndex = 1";
mostCurrent._tab2.setTabIndex((int) (1));
 //BA.debugLineNum = 84;BA.debugLine="AHViewPager1.GotoPage (1, True)";
mostCurrent._ahviewpager1.GotoPage((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim tab1 As JSDevLightNavTabStrip";
mostCurrent._tab1 = new com.salvadorjhai.widgets.JSDevLightNavTabStrip();
 //BA.debugLineNum = 25;BA.debugLine="Dim tab2 As JSDevLightNavTabStrip";
mostCurrent._tab2 = new com.salvadorjhai.widgets.JSDevLightNavTabStrip();
 //BA.debugLineNum = 26;BA.debugLine="Private AHViewPager1 As AHViewPager";
mostCurrent._ahviewpager1 = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 27;BA.debugLine="Private AHViewPager2 As AHViewPager";
mostCurrent._ahviewpager2 = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _tab1_onendtabselected(String _title,int _index) throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub tab1_OnEndTabSelected(title As String, index A";
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
}
