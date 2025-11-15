# B4A-JSDevLightNavTabStrip

NavigationTabStrip - Navigation tab strip with smooth interaction

Wrap of this: https://github.com/DevLight-Mobile-Agency/NavigationTabStrip

Sharing it to you guys "as-is".

You can handle the tab select/change with 'OnStartTabSelected' OR 'OnEndTabSelected' event.

AHViewPager is now supported.
Designer Custom View Supported.

```vb
    '-- Sample 1 (Top)
    Dim tab1 As JSDevLightNavTabStrip
    tab1.Initialize("tab1")
    Activity.AddView(tab1, 0dip, 0dip, 100%x, 56dip)
    tab1.Titles = Array As String("Videos", "Images", "Song")
    tab1.ActiveColor = 0xFFE91E63
    tab1.StripColor = 0xFFE91E63
    tab1.TabIndex = 0

    '-- Sample 1 (Bottom)
    Dim tab2 As JSDevLightNavTabStrip
    tab2.Initialize("tab2")
    Activity.AddView(tab2, 0dip, tab1.Top + tab1.Height + 20%y, 100%x, 56dip)
    tab2.Titles = Array As String("Eat", "Sleep", "Conquer", "Repeat")
    tab2.TitleSize = 13dip
    tab2.TabIndex = 4

    '-- Sample 2 (Bottom)
    Dim tab3 As JSDevLightNavTabStrip
    tab3.Initialize("tab3")
    Activity.AddView(tab3, 0dip, 100%y - 56dip, 100%x, 56dip)
    tab3.Color = Colors.White
    tab3.Titles = Array As String("Home", "Think", "Design", "Animate")
    tab3.ActiveColor = 0xFF8BC34A
    tab3.InactiveColor = Colors.LightGray
    tab3.StripType = tab3.STRIP_TYPE_LINE
    tab3.StripGravity = tab3.STRIP_GRAVITY_BOTTOM
    tab3.StripColor = 0xFF8BC34A
    tab3.TabIndex = 1
```

More info;
https://www.b4x.com/android/forum/threads/jsdevlightnavtabstrip-navigation-tab-strip-with-smooth-interaction.73334/#content
