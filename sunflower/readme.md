## 목차

---

🔗 참고

[sunflower-github](https://github.com/android/sunflower)

### 1. View
- MVVM에서 View에 해당하는 Activity, Fragment, Adapter
- 이 프로젝트는 SPA(Single-Page-Application) 구조!

**[Garden Activity]**
- 유일한 Activity인 GardenActivity
- 프래그먼트의 껍데기 역할을 함
```kotlin
@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
    }
}
```

- Jetpack Navigation을 활용하기 위한 옵션들과, FragmentCotainerView를 통해 프래그먼트를 담는다!
```xml
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_garden" />

</layout>
```


**[HomeViewPagerFragment]**
- 여기서 탭과 뷰페이저를 붙인다!
- SunflowerPagerAdapter에서 뷰페이저2 안에 들어 갈 fragment를 invoke
<img src="https://user-images.githubusercontent.com/85485290/161228972-944d4ecc-239f-438f-b044-4a9da788ff8c.png" width="200">

**[SunflowerPagerAdpater]**

> ✨ createFragment의 로직 참고
- 1번 GardenFragment / 2번 PlantListFragment를 create
```kotlin
const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

class SunflowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() },
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
```

**[PlantListFragment]**

<img src="https://user-images.githubusercontent.com/85485290/161231593-d80bff8f-7b9a-4ce9-9c30-dbae49ae4f54.png" width="300">


