package com.salvadorjhai.widgets;

/*
 * r3	- implemented designer custom view
 * r2	- added ViewPager Support
 * r1	- Initial
 * 
 */

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.gigamole.navigationtabstrip.NavigationTabStrip.OnTabStripSelectedIndexListener;
import com.gigamole.navigationtabstrip.NavigationTabStrip.StripGravity;
import com.gigamole.navigationtabstrip.NavigationTabStrip.StripType;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ActivityObject;
import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.DependsOn;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import anywheresoftware.b4a.keywords.Common.DesignerCustomView;
import anywheresoftware.b4a.objects.CustomViewWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.collections.Map;

/**
 * Navigation tab strip with smooth interaction. Wrapped:
 * https://github.com/DevLight-Mobile-Agency/NavigationTabStrip Credits to:
 * DevLight-Mobile-Agency
 * 
 * Sample 1: <code>
 * 	Dim tab1 As JSDevLightNavTabStrip
 * 	tab1.Initialize("tab1")
 *	Activity.AddView(tab1, 0dip, 0dip, 100%x, 56dip)	
 * 	tab1.Titles = Array As String("Videos", "Images", "Song")
 * 	tab1.ActiveColor = 0xFFE91E63
 *	tab1.StripColor = 0xFFE91E63	
 * 	tab1.TabIndex = 0</code>
 * 
 * Sample 2:<code>
 * 	Dim tab3 As JSDevLightNavTabStrip
 *	tab3.Initialize("tab3")
 *	Activity.AddView(tab3, 0dip, 100%y - 56dip, 100%x, 56dip)	
 *	tab3.Color = Colors.White
 *	tab3.Titles = Array As String("Home", "Think", "Design", "Animate")
 *	tab3.ActiveColor = 0xFF8BC34A
 *	tab3.InactiveColor = Colors.LightGray
 *	tab3.StripType = tab3.STRIP_TYPE_LINE
 *	tab3.StripGravity = tab3.STRIP_GRAVITY_BOTTOM
 *	tab3.StripColor = 0xFF8BC34A	
 *	tab3.TabIndex = 0</code>
 */

@ShortName("JSDevLightNavTabStrip")
@Version(0.3f)
@Author("salvadorjhai")
@Events(values = { "OnStartTabSelected(title As String, index As int)",
		"OnEndTabSelected(title As String, index As int)" })

// @Events(values = { "OnStartTabSelected(title As String, index As int)",
// "OnEndTabSelected(title As String, index As int)",
// "OnPageSelected(position As int)",
// "OnPageScrolled(position As int, positionOffset As float,
// positionOffsetPixels As int)",
// "OnPageScrollStateChanged(scroll_state As int)"})

@DependsOn(values = { "JSDLnavigationtabstrip.aar", "android-support-v4" })
@ActivityObject
public class JSDevLightNavTabStrip extends ViewWrapper<NavigationTabStrip> implements DesignerCustomView {

	/** The Constant STRIP_GRAVITY_BOTTOM. */
	public final static int STRIP_GRAVITY_BOTTOM = 0;

	/** The Constant STRIP_GRAVITY_TOP. */
	public final static int STRIP_GRAVITY_TOP = 1;

	/** The Constant STRIP_TYPE_LINE. */
	public final static int STRIP_TYPE_LINE = 0;

	/** The Constant STRIP_TYPE_POINT. */
	public final static int STRIP_TYPE_POINT = 1;

	public static int PAGE_SCROLL_STATE_DRAGGING = 0x00000001;
	public static int PAGE_SCROLL_STATE_IDLE = 0x00000000;
	public static int PAGE_SCROLL_STATE_SETTLING = 0x00000002;

	private BA mBA = null;
	private String mEventName = "";

	@Override
	public void Initialize(BA ba, String eventName) {
		_initialize(ba, null, eventName);
	}

	@Override
	public void DesignerCreateView(PanelWrapper paramPanelWrapper, LabelWrapper paramLabelWrapper, Map paramMap) {
		CustomViewWrapper.replaceBaseWithView2(paramPanelWrapper, this.getObjectOrNull());
	}

	@Override
	@BA.Hide
	public void _initialize(BA ba, Object paramObject, String eventName) {
		this.mBA = ba;
		this.mEventName = eventName;
		
		try {
			// set new object
			this.setObject(new NavigationTabStrip(ba.context));

			if (eventName.length() != 0) {
				this.getObjectOrNull().setOnTabStripSelectedIndexListener(new OnTabStripSelectedIndexListener() {

					@Override
					public void onStartTabSelected(String title, int index) {
						String event = mEventName + "_OnStartTabSelected";
						mBA.raiseEventFromUI(this, event.toLowerCase(), new Object[] { title, index });
					}

					@Override
					public void onEndTabSelected(String title, int index) {
						String event = mEventName + "_OnEndTabSelected";
						mBA.raiseEventFromUI(this, event.toLowerCase(), new Object[] { title, index });
					}
				});
			}

		    // Inner Initialize
		    super.innerInitialize(ba, eventName, true);
			
		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}
	}

	/**
	 * Deselect.
	 */
	public void Deselect() {
		// TODO Auto-generated method stub
		this.getObjectOrNull().deselect();

	}

	/**
	 * Gets the active color.
	 *
	 * @return the active color
	 */
	public int getActiveColor() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getActiveColor();
	}

	/**
	 * Gets the animation duration.
	 *
	 * @return the animation duration
	 */
	public int getAnimationDuration() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getAnimationDuration();
	}

	/**
	 * Gets the corners radius.
	 *
	 * @return the corners radius
	 */
	public float getCornersRadius() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getCornersRadius();
	}

	/**
	 * Gets the inactive color.
	 *
	 * @return the inactive color
	 */
	public int getInactiveColor() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getInactiveColor();
	}

	/**
	 * Gets the strip color.
	 *
	 * @return the strip color
	 */
	public int getStripColor() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getStripColor();
	}

	/**
	 * Gets the strip factor.
	 *
	 * @return the strip factor
	 */
	public float getStripFactor() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getStripFactor();
	}

	/**
	 * Gets the strip gravity.
	 *
	 * @return the strip gravity
	 */
	public int getStripGravity() {
		if (this.getObjectOrNull().getStripGravity() == StripGravity.BOTTOM) {
			return STRIP_GRAVITY_BOTTOM;
		} else {
			return STRIP_GRAVITY_TOP;
		}
	}

	/**
	 * Gets the strip type.
	 *
	 * @return the strip type
	 */
	public int getStripType() {
		if (this.getObjectOrNull().getStripType() == StripType.LINE) {
			return STRIP_TYPE_LINE;
		} else {
			return STRIP_TYPE_POINT;
		}
	}

	/**
	 * Gets the tab index.
	 *
	 * @return the tab index
	 */
	public int getTabIndex() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getTabIndex();
	}

	/**
	 * Gets the title size.
	 *
	 * @return the title size
	 */
	public float getTitleSize() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getTitleSize();
	}

	/**
	 * Gets the titles.
	 *
	 * @return the titles
	 */
	public String[] getTitles() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getTitles();
	}

	/**
	 * Gets the typeface.
	 *
	 * @return the typeface
	 */
	public Typeface getTypeface() {
		// TODO Auto-generated method stub
		return this.getObjectOrNull().getTypeface();
	}

	/**
	 * Sets the active color.
	 *
	 * @param activeColor
	 *            the new active color
	 */
	public void setActiveColor(int activeColor) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setActiveColor(activeColor);
	}

	/**
	 * Sets the animation duration.
	 *
	 * @param animationDuration
	 *            the new animation duration
	 */
	public void setAnimationDuration(int animationDuration) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setAnimationDuration(animationDuration);
	}

	/**
	 * Sets the corners radius.
	 *
	 * @param cornersRadius
	 *            the new corners radius
	 */
	public void setCornersRadius(float cornersRadius) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setCornersRadius(cornersRadius);
	}

	/**
	 * Sets the inactive color.
	 *
	 * @param inactiveColor
	 *            the new inactive color
	 */
	public void setInactiveColor(int inactiveColor) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setInactiveColor(inactiveColor);
	}

	/**
	 * Sets the strip color.
	 *
	 * @param color
	 *            the new strip color
	 */
	public void setStripColor(int color) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setStripColor(color);
	}

	/**
	 * Sets the strip factor.
	 *
	 * @param factor
	 *            the new strip factor
	 */
	public void setStripFactor(float factor) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setStripFactor(factor);
	}

	/**
	 * Sets the strip gravity.
	 *
	 * @param stripGravity
	 *            the new strip gravity
	 */
	public void setStripGravity(int stripGravity) {
		if (stripGravity == STRIP_GRAVITY_TOP) {
			this.getObjectOrNull().setStripGravity(StripGravity.TOP);
		} else {
			this.getObjectOrNull().setStripGravity(StripGravity.BOTTOM);
		}

	}

	/**
	 * Sets the strip type.
	 *
	 * @param stripType
	 *            the new strip type
	 */
	public void setStripType(int stripType) {
		if (stripType == STRIP_TYPE_LINE) {
			this.getObjectOrNull().setStripType(StripType.LINE);
		} else {
			this.getObjectOrNull().setStripType(StripType.POINT);
		}
	}

	/**
	 * Sets the strip weight.
	 *
	 * @param stripWeight
	 *            the new strip weight
	 */
	public void setStripWeight(float stripWeight) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setStripWeight(stripWeight);
	}

	/**
	 * Sets the tab index.
	 *
	 * @param index
	 *            the new tab index
	 */
	public void setTabIndex(int index) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setTabIndex(index);
	}

	/**
	 * Sets the title size.
	 *
	 * @param titleSize
	 *            the new title size
	 */
	public void setTitleSize(float titleSize) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setTitleSize(titleSize);
	}

	/**
	 * Sets the titles.
	 *
	 * @param arg0
	 *            the new titles
	 */
	public void setTitles(String[] titles) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setTitles(titles);
	}

	/**
	 * Sets the typeface.
	 *
	 * @param typeface
	 *            the new typeface
	 */
	public void setTypeface(Typeface typeface) {
		// TODO Auto-generated method stub
		this.getObjectOrNull().setTypeface(typeface);
	}

	/**
	 * Sets the view pager.
	 *
	 * @param viewPager
	 *            the new view pager
	 */
	public void setViewPager(Object viewPager) {
		try {
			this.getObjectOrNull().setViewPager((ViewPager) viewPager);

			// this.getObjectOrNull().setOnPageChangeListener(new
			// OnPageChangeListener() {
			//
			// @Override
			// public void onPageSelected(int position) {
			// String event = mEventName + "_OnPageSelected";
			// ba.raiseEventFromDifferentThread(this, null, 0,
			// event.toLowerCase(), false, new Object[] {position});
			// }
			//
			// @Override
			// public void onPageScrolled(int position, float positionOffset,
			// int positionOffsetPixels) {
			// String event = mEventName + "_OnPageScrolled";
			// ba.raiseEventFromDifferentThread(this, null, 0,
			// event.toLowerCase(), false, new Object[] {position,
			// positionOffset, positionOffsetPixels});
			// }
			//
			// @Override
			// public void onPageScrollStateChanged(int state) {
			// String event = mEventName + "_OnPageScrollStateChanged";
			// ba.raiseEventFromDifferentThread(this, null, 0,
			// event.toLowerCase(), false, new Object[] {state});
			// }
			//
			// });

		} catch (Exception e) {
			BA.LogError(e.getMessage());
		}
	}

}
