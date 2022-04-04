## 목차
  * [1. Navigation](#1-navigation)
  * [2. View](#2-view)
    + [DiffUtil](#diffutil)
    + [ListAdapter](#listadapter)
  
  * [3. ViewModel](#3-viewmodel)
  * [4. Model](#4-model)

---

🔗 참고

[sunflower-github](https://github.com/android/sunflower)

---
### 1. Navigation

**[Jetpack Navigation 구조]**

🔗 참고 링크

[navigation](http://developer.android.com/guide/navigation?hl=ko)

<img src="https://user-images.githubusercontent.com/85485290/161585686-7bd7f92c-03e7-4382-981d-20c2d13b31c1.png" width="400" >

navigation 구성 요소는 세가지 주요 부분으로 구성된다는데,,

- Navigation Graph: 모든 탐색 관련 정보가 하나의 중심 위치에 모여 있는 **XML 리소스**. 여기에는 *대상*이라고 부르는 앱 내의 모든 개별적 콘텐츠 영역과 사용자가 앱에서 갈 수 있는 모든 이용 가능한 경로가 포함된다.
- `NavHost`: 탐색 그래프에서 대상을 표시하는 빈 컨테이너. 대상 구성요소에는 프래그먼트 대상을 표시하는 기본 `NavHost` 구현인 `[NavHostFragment](https://developer.android.com/reference/androidx/navigation/fragment/NavHostFragment?hl=ko)`가 포함된다.
- `NavController`: `NavHost`에서 앱 탐색을 관리하는 객체. `NavController`는 사용자가 앱 내에서 이동할 때 `NavHost`에서 대상 콘텐츠의 전환을 오케스트레이션한다(?).


앱을 탐색하는 동안 네비게이션 그래프에서 특정 경로를 따라 이동할지, 특정 대상으로 직접 이동할지 `NavController`에게 전달 → 그러면 `NavController`가 `NavHost`에 적절한 대상을 표시한다.


> sunflower 프로젝트는 **SPA(Single-Page-Application)** 싱글 액티비티 구조 → 하나의 액티비티와 다수의 프래그먼트가 존재한다!

여기서는 Jetpack Navigation에서 제공해주는 바텀 네비게이션과 툴바를 사용하지는 않았고, Main Activity 위에 **Toolbar + ViewPager2 + TabLayout** 구조를 가진 Fragment가 세팅되어 있고 프래그먼트가 전환되는 식이다!

+프래그먼트에서 아이템을 누르면 Detail 화면으로 가는 네비게이션을 가진다.

- Jetpack Navigation은 싱글 액티비티 디자인, 프래그먼트를 활용할 때 강한 장점이 있다✨



**[GardenActivity XML]**

- 단 하나의 (메인)액티비티를 담당
- XML에 navGraph와 defaultNavHost가 설정되어 있다!
    - app:navGraph 속성은 NavHostFragment를 탐색 그래프와 연결합니다. 탐색 그래프는 사용자가 이동할 수 있는 이 NavHostFragment의 모든 대상을 지정합니다.
    - app:defaultNavHost="true" 속성을 사용하면 NavHostFragment가 시스템 뒤로 버튼을 가로챕니다. 하나의 NavHost만 기본값으로 지정할 수 있습니다. 동일한 레이아웃에 여러 호스트가 있다면(예: 창이 2개인 레이아웃) 한 호스트만 기본 NavHost로 지정해야 합니다
- [navigation-정리글](http://youngest-programming.tistory.com/274)
- FragmentContainerView에 ViewPager2가 세팅된다!
- navGraph에 nav_garden xml 리소스를 연결시켜준다

```xml
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

**[nav_garden XML]**

<img src="https://user-images.githubusercontent.com/85485290/161586070-cf07e9e1-c0d8-4931-94db-2320da6615c4.png" width="400">

- 위와 같이 3개의 화면으로, 각 화면 이동간에 anim이 구현되어 있음
- 화면 이동하며 넘겨지는 arguments 들도 설정되어 있음
- [참고링크](http://youngest-programming.tistory.com/332)
- [anim-참고링크](http://youngest-programming.tistory.com/483)


```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    app:startDestination="@id/view_pager_fragment">

    <fragment
        android:id="@+id/view_pager_fragment"
        android:name="com.google.samples.apps.sunflower.HomeViewPagerFragment"
        tools:layout="@layout/fragment_view_pager">

        <action
                android:id="@+id/action_view_pager_fragment_to_plant_detail_fragment"
                app:destination="@id/plant_detail_fragment"
                app:enterAnim="@anim/slide_in_right"
                app:exitAnim="@anim/slide_out_left"
                app:popEnterAnim="@anim/slide_in_left"
                app:popExitAnim="@anim/slide_out_right" />
    </fragment>

    <fragment
        android:id="@+id/plant_detail_fragment"
        android:name="com.google.samples.apps.sunflower.PlantDetailFragment"
        android:label="@string/plant_details_title"
        tools:layout="@layout/fragment_plant_detail">

        <action
            android:id="@+id/action_plant_detail_fragment_to_gallery_fragment"
            app:destination="@id/gallery_fragment"
            app:enterAnim="@anim/slide_in_right"
            app:exitAnim="@anim/slide_out_left"
            app:popEnterAnim="@anim/slide_in_left"
            app:popExitAnim="@anim/slide_out_right" />
        <argument
            android:name="plantId"
            app:argType="string" />
    </fragment>

    <fragment
        android:id="@+id/gallery_fragment"
        android:name="com.google.samples.apps.sunflower.GalleryFragment"
        android:label="@string/plant_details_title"
        tools:layout="@layout/fragment_gallery">
        <argument
            android:name="plantName"
            app:argType="string" />
    </fragment>

</navigation>
```



- HomeViewPagerFragment(에서 Plant List 프래그먼트) → PlantDetailFragment(식물 상세 화면) 를 보면,,

```xml
<fragment
        android:id="@+id/plant_detail_fragment"
        android:name="com.google.samples.apps.sunflower.PlantDetailFragment"
        android:label="@string/plant_details_title"
        tools:layout="@layout/fragment_plant_detail">

        <action
            android:id="@+id/action_plant_detail_fragment_to_gallery_fragment"
            app:destination="@id/gallery_fragment"
            app:enterAnim="@anim/slide_in_right"
            app:exitAnim="@anim/slide_out_left"
            app:popEnterAnim="@anim/slide_in_left"
            app:popExitAnim="@anim/slide_out_right" />
        <argument
            android:name="plantId"
            app:argType="string" />
    </fragment>
```

- **PlantDetailFragment**는 plantId라는 String 타입 매개변수를 전달 받는다.



- 그리고 **HomeViewPager**는 `<action>`으로 PlantDetailFragment로 Direction이 설정되어 있는데,
- 이렇게 설정하면 빌드 시 자동으로 NavController에서 네비게이션 하는 함수가 만들어진다!

```xml
 <fragment
        android:id="@+id/view_pager_fragment"
        android:name="com.google.samples.apps.sunflower.HomeViewPagerFragment"
        tools:layout="@layout/fragment_view_pager">

        <action
                android:id="@+id/action_view_pager_fragment_to_plant_detail_fragment"
                app:destination="@id/plant_detail_fragment"
                app:enterAnim="@anim/slide_in_right"
                app:exitAnim="@anim/slide_out_left"
                app:popEnterAnim="@anim/slide_in_left"
                app:popExitAnim="@anim/slide_out_right" />
    </fragment>
```



- PlantListFragment  리싸이클러뷰의 **PlantAdapter**에 아이템 클릭 시 PlantDetailFragment로 이동하는 이벤트가 구현되어 있다.

```kotlin
// PlantAdapter
private fun navigateToPlant(
            plant: Plant,
            view: View
        ) {
            val direction =
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                    plant.plantId
                )
            view.findNavController().navigate(direction)
        }
```



- **PlantDetailFragment**에서는 by navArgs로 값을 전달받는다!

```kotlin
@AndroidEntryPoint
class PlantDetailFragment : Fragment() {

    private val args: PlantDetailFragmentArgs by navArgs()

    @Inject
    lateinit var plantDetailViewModelFactory: PlantDetailViewModelFactory

    private val plantDetailViewModel: PlantDetailViewModel by viewModels {
        PlantDetailViewModel.provideFactory(plantDetailViewModelFactory, args.plantId)
    }
```

---
### 2. View
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

**[PlantAdapter]**

- ListAdpater를 상속받아서 **DiffUtil**을 적용했다!

🔗 참고 링크

[[Kotlin Android] RecyclerView 어댑터의 데이터 빠르게 바꾸기 - ListAdapter와 DiffUtil 사용하기](https://velog.io/@l2hyunwoo/Android-RecyclerView-DiffUtil-ListAdapter)

[](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding#0)

[](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter)

**Adapter에서 notifyDataSetChanged() 를 쓰면 힘든(?) 이유**

RecyclerView에서 어댑터가 하는 일은

- 미리 생성해 둔 뷰홀더 객체에
- 사용자가 원하는 데이터 리스트를 주입하고
- 이 데이터 리스트의 변경사항이 있을 때 이를 UI에 반영

하는 것인데,

어댑터에서 notifyDataSetChanged()를 사용하면

> 리스트 안에 데이터가 바뀌었으니까 전체 리스트 다 바꿔!
> 

가 되버리는 것

그런데 만약에 한 번에 받은 Item들이 100~200개 이상이라면 통째로 업데이트할 때 지연 시간이 당연히 발생할 수밖에 없었고, 그래서 RecyclerView에서는 지속적으로 Flickering Issue가 있어왔음

그래서 이런 비용을 줄이고자 Android 진영에서 내놓은 해결책은 **DiffUtil.**

#### DiffUtil

RecyclerView.DiffUtil은

- 현재 데이터 리스트와 교체될 데이터 리스트를 비교하고
- 진짜 바뀌어야 할 데이터만 바꿔줌으로써
- *notify 뭐시기* 보다 훨씬 빠른 시간 내에 데이터 교환을 할 수 있게 한다!

ListAdapter는 DiffUtil을 활용하여 리스트를 업데이트할 수 있는 기능을 추가한 Adapter라고 생각하면 됨.

기존 어댑터와 비교해서 추가로 DiffUtil 기능에 대한 콜백 기능 클래스만 구현하면 되므로 생산성, 효율성을 높일 수 있을 듯

DirffUitl을 사용하기 위해서는 클라에서 DiffUtil.Callback라는 기능을 구현해야 함

- `areItemsTheSame` : 이전 어댑터와 바뀌는 어댑터의 아이템이 동일한 지 확인함. 각 아이템의 고유 ID값(DB에서 key 같은 것을 받아온다면 그걸 활용)이 있을텐데 이걸 활용하여 비교하면 됨
- `areContentsTheSame` : 이전 어댑터와 바뀌는 어댑터의 아이템 내 내용을 비교한다. `areItemsTheSame` 에서 true가 나올 경우 추가적으로 비교하기 위해서 사용하는 함수.

만약 여기서 `areItemsTheSame` 를 잘못 정의하면 모든 Item을 다 지우고 새 아이템을 Insert하는 일이 초래되어서 notifyDataSetChanged()와 별반 다를 바 없어질 수가 있으므로,,,주의!

- PlantAdapter에서 구현한 모습

```kotlin
private class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {

    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.plantId == newItem.plantId
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }
}
```

#### ListAdapter

- 리스트어댑터는 다음과 같은 형태를 지님

`ListAdapter<데이터 클래스, 리사이클러뷰 뷰홀더>(개발자 정의 콜백)`

- 데이터 클래스를 받는게 특징
- 사용자가 어댑터 내에서 리스트를 정의하지 않고 리스트 자체에서 데이터 리스트를 정의하기 때문!

리스트 어댑터의 주요 메소드

- `getItem(position: Int)` : protected method라 클래스 내부에서 구현할 때 사용. 어댑터 내 List Indexing을 할 때 활용됨
- `getCurrentList()` : 어댑터가 가지고 있는 리스트를 가져올 때 사용함.
- `submitList(MutableList<T> list)` : 리스트 항목을 변경하고 싶을 때 사용함✨

- PlantListFragment에서 다음과 같이 viewmodel의 데이터에 변경이 있으면 submitList로 리스트 항목을 바꿔줌!

```kotlin
private fun subscribeUi(adapter: PlantAdapter) {
    viewModel.plants.observe(viewLifecycleOwner){plants->
adapter.submitList(plants)
}
}
```

**[PlantDetailFragment]**

- PlantAdapter 안에서 아이템 하나를 누르면 PlantDetailFragment로 이동하는 것을 볼 수 있음

```kotlin
init {
            binding.setClickListener {
                binding.plant?.let { plant ->
                    navigateToPlant(plant, it)
                }
            }
        }

        private fun navigateToPlant(
            plant: Plant,
            view: View
        ) {
            val direction =
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                    plant.plantId
                )
            view.findNavController().navigate(direction)
        }
```

<img src="https://user-images.githubusercontent.com/85485290/161273539-a70873e4-0c88-42a0-b67c-0245603829f9.png" width="200"><img src="https://user-images.githubusercontent.com/85485290/161273634-2d0d7e0d-26ad-43de-838c-ca528a6c87c5.png" width="200">


- 스크롤에 따라 화면의 툴바(앱바)가 접히고 펼쳐지는 애니메이션을 가지는 CollapsingToolbarLayout이 사용되었음

<img src="https://user-images.githubusercontent.com/85485290/161272969-ad598d0a-ffe9-4cea-987b-b00a04420ecf.png" width="300">

의 구조로 되어있음!


🔗 CollapsingToolbar 참고 링크

[CollapsingToolbar 예제1](https://youngest-programming.tistory.com/353)

[CollapsingToolbar 예제2](https://youngest-programming.tistory.com/380)

- +버튼 모양을 누르면 ViewModel의 addPlantToGarden()을 호출 해 식물이 My Garden에 저장되고 스낵바 메세지를 띄운다. 그리고 + 버튼은 사라진다!

```kotlin
plant?.let {
                    hideAppBarFab(fab)
                    plantDetailViewModel.addPlantToGarden()
                    Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
```

- [PlantDetailViewModel]을 잠깐 보면 다음과 같다

```kotlin
fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }
```

- 툴바 오른쪽 끝의 공유 버튼을 누르면 **createShareIntent()**가 호출되어 공유하기 기능이 실행된다.

<img src="https://user-images.githubusercontent.com/85485290/161273833-bb6343d0-923a-4841-87fb-efe5167c0c24.png" width="300">

**[GardenFragment]**

<img src="https://user-images.githubusercontent.com/85485290/161273964-7b472817-953b-4638-a71f-47c973015e7c.png" width="300">

- hasPlantings의 값에 따라 보이는 뷰가 다름 → 데이터 바인딩으로 처리

<img src="https://user-images.githubusercontent.com/85485290/161274034-a8cc6ec2-e307-4444-aa4c-4947aee93c52.png" width="400">

- BindingAdapters.kt에 정의되어 있는 app:isGone

```kotlin
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
```

- GardenFragment에서 데이터 리스트가 비었는지, 안비었는지 처리할 수 있음!

```kotlin
private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
    viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner){result->
binding.hasPlantings= !result.isNullOrEmpty()
        adapter.submitList(result)
}
}
```

---
### 3. ViewModel

🔗 참고링크

- flow

[stateflow-and-sharedflow](http://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

[flow-정리글](http://yoon-dailylife.tistory.com/72)

- coroutine

[architecture-with-coroutine](http://developer.android.com/topic/libraries/architecture/coroutines?hl=ko)

[coroutine](http://developer.android.com/kotlin/coroutines?hl=ko)



- 유일한 액티비티인 GardenActivity에 ViewModel은 없다.


**[PlainListViewModel]**

<img src="https://user-images.githubusercontent.com/85485290/161577629-c90b2f8c-9d45-43b7-9440-e395f7b2d4d9.png" width="300">


- 생성자로 MVVM에서 `Model`에 해당하는 PlantRepository와 SavedStateHandle을 가진다.

```kotlin
@HiltViewModel
class PlantListViewModel @Inject internal constructor(
    plantRepository: PlantRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
```


- 데이터는 MutableStateFlow를 사용하는데, 상태를 업데이트 하고 Flow에 전송하는 것임.
- Flow는 일반적으로 cold stream이지만, StateFlow는 `hot stream`이다. 일반 Flow는 마지막 값의 개념이 없고 collect 될 때만 활성화 되는 반면, StateFlow는 **마지막 값**의 개념이 있으며 **생성하자마자 활성화** 된다고 함!!
- Flow는 Cold니 Subject ↔ StateFlow는 Hot이니 Observable
- Stream하고 데이터를 저장한다는 기능으로 기존의 **LiveData + RxJava 느낌,,??**
- LiveData와 Flow의 차이점 중 하나가 Flow는 flatMapLatest와 같은 Stream 함수를 제공한다는 점!


```kotlin
private val growZone: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY) ?: NO_GROW_ZONE
    )

val plants: LiveData<List<Plant>> = growZone.flatMapLatest { zone ->
    if (zone == NO_GROW_ZONE) {
        plantRepository.getPlants()
    } else {
            plantRepository.getPlantsWithGrowZoneNumber(zone)
      }
}.asLiveData()
```

- ViewModelScope는 앱의 각 ViewModel을 대상으로 정의된다. 문서를 인용해보자면,,


> 이 범위에서 시작된 모든 코루틴은 ViewModel이 삭제되면 자동으로 취소됩니다. 코루틴은 ViewModel이 활성 상태인 경우에만 실행해야 할 작업이 있을 때 유용합니다. 예를 들어 레이아웃의 일부 데이터를 계산한다면 작업의 범위를 ViewModel로 지정하여 ViewModel을 삭제하면 리소스를 소모하지 않도록 작업이 자동으로 취소됩니다.
> 


라고 하넹

```kotlin

    init {
        viewModelScope.launch {
            growZone.collect { newGrowZone ->
                savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
            }
        }
    }
```


<img src="https://user-images.githubusercontent.com/85485290/161577780-b865e73b-a0c1-47d4-8faa-03d2092fbeac.png" width="400">
StateFlow와 MutableStateFlow!

<img src="https://user-images.githubusercontent.com/85485290/161577833-15652cb0-8932-42aa-98e8-c3cdfbfddc76.png" width="400">
LiveData와 Flow의 차이점?

---
### 4. Model


