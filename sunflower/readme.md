## 목차
  * [1. View](#1-view)
    + [DiffUtil](#diffutil)
    + [ListAdapter](#listadapter)
  
  * [2. ViewModel](#2-viewmodel)
  * [3. Model](#3-model)

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
### 2. ViewModel


---
### 3. Model


