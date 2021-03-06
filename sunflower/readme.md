## λͺ©μ°¨
  * [1. Navigation](#1-navigation)
  * [2. View](#2-view)
    + [DiffUtil](#diffutil)
    + [ListAdapter](#listadapter)
  
  * [3. ViewModel](#3-viewmodel)
  * [4. Model](#4-model)

---

π μ°Έκ³ 

[sunflower-github](https://github.com/android/sunflower)

---
### 1. Navigation

**[Jetpack Navigation κ΅¬μ‘°]**

πΒ μ°Έκ³  λ§ν¬

[navigation](http://developer.android.com/guide/navigation?hl=ko)

<img src="https://user-images.githubusercontent.com/85485290/161585686-7bd7f92c-03e7-4382-981d-20c2d13b31c1.png" width="400" >

navigation κ΅¬μ± μμλ μΈκ°μ§ μ£Όμ λΆλΆμΌλ‘ κ΅¬μ±λλ€λλ°,,

- Navigation Graph: λͺ¨λ  νμ κ΄λ ¨ μ λ³΄κ° νλμ μ€μ¬ μμΉμ λͺ¨μ¬ μλ **XML λ¦¬μμ€**. μ¬κΈ°μλΒ *λμ*μ΄λΌκ³  λΆλ₯΄λ μ± λ΄μ λͺ¨λ  κ°λ³μ  μ½νμΈ  μμ­κ³Ό μ¬μ©μκ° μ±μμ κ° μ μλ λͺ¨λ  μ΄μ© κ°λ₯ν κ²½λ‘κ° ν¬ν¨λλ€.
- `NavHost`: νμ κ·Έλνμμ λμμ νμνλ λΉ μ»¨νμ΄λ. λμ κ΅¬μ±μμμλ νλκ·Έλ¨ΌνΈ λμμ νμνλ κΈ°λ³ΈΒ `NavHost`Β κ΅¬νμΈΒ `[NavHostFragment]`κ° ν¬ν¨λλ€.
- `NavController`:Β `NavHost`μμ μ± νμμ κ΄λ¦¬νλ κ°μ²΄.Β `NavController`λ μ¬μ©μκ° μ± λ΄μμ μ΄λν  λΒ `NavHost`μμ λμ μ½νμΈ μ μ νμ μ€μΌμ€νΈλ μ΄μνλ€(?).


μ±μ νμνλ λμ λ€λΉκ²μ΄μ κ·Έλνμμ νΉμ  κ²½λ‘λ₯Ό λ°λΌ μ΄λν μ§, νΉμ  λμμΌλ‘ μ§μ  μ΄λν μ§Β `NavController`μκ² μ λ¬ β κ·Έλ¬λ©΄Β `NavController`κ°Β `NavHost`μ μ μ ν λμμ νμνλ€.


> sunflower νλ‘μ νΈλ **SPA(Single-Page-Application)** μ±κΈ μ‘ν°λΉν° κ΅¬μ‘° β νλμ μ‘ν°λΉν°μ λ€μμ νλκ·Έλ¨ΌνΈκ° μ‘΄μ¬νλ€!

μ¬κΈ°μλ Jetpack Navigationμμ μ κ³΅ν΄μ£Όλ λ°ν λ€λΉκ²μ΄μκ³Ό ν΄λ°λ₯Ό μ¬μ©νμ§λ μμκ³ , Main Activity μμ **Toolbar + ViewPager2 + TabLayout** κ΅¬μ‘°λ₯Ό κ°μ§ Fragmentκ° μΈνλμ΄ μκ³  νλκ·Έλ¨ΌνΈκ° μ νλλ μμ΄λ€!

+νλκ·Έλ¨ΌνΈμμ μμ΄νμ λλ₯΄λ©΄ Detail νλ©΄μΌλ‘ κ°λ λ€λΉκ²μ΄μμ κ°μ§λ€.

- Jetpack Navigationμ μ±κΈ μ‘ν°λΉν° λμμΈ, νλκ·Έλ¨ΌνΈλ₯Ό νμ©ν  λ κ°ν μ₯μ μ΄ μλ€β¨



**[GardenActivity XML]**

- λ¨ νλμ (λ©μΈ)μ‘ν°λΉν°λ₯Ό λ΄λΉ
- XMLμ navGraphμ defaultNavHostκ° μ€μ λμ΄ μλ€!
    - app:navGraphΒ μμ±μΒ NavHostFragmentλ₯Ό νμ κ·Έλνμ μ°κ²°ν©λλ€. νμ κ·Έλνλ μ¬μ©μκ° μ΄λν  μ μλ μ΄Β NavHostFragmentμ λͺ¨λ  λμμ μ§μ ν©λλ€.
    - app:defaultNavHost="true"Β μμ±μ μ¬μ©νλ©΄Β NavHostFragmentκ° μμ€ν λ€λ‘ λ²νΌμ κ°λ‘μ±λλ€. νλμΒ NavHostλ§ κΈ°λ³Έκ°μΌλ‘ μ§μ ν  μ μμ΅λλ€. λμΌν λ μ΄μμμ μ¬λ¬ νΈμ€νΈκ° μλ€λ©΄(μ: μ°½μ΄ 2κ°μΈ λ μ΄μμ) ν νΈμ€νΈλ§ κΈ°λ³ΈΒ NavHostλ‘ μ§μ ν΄μΌ ν©λλ€
- [navigation-μ λ¦¬κΈ](http://youngest-programming.tistory.com/274)
- FragmentContainerViewμ ViewPager2κ° μΈνλλ€!
- navGraphμ nav_garden xml λ¦¬μμ€λ₯Ό μ°κ²°μμΌμ€λ€

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

- μμ κ°μ΄ 3κ°μ νλ©΄μΌλ‘, κ° νλ©΄ μ΄λκ°μ animμ΄ κ΅¬νλμ΄ μμ
- νλ©΄ μ΄λνλ©° λκ²¨μ§λ arguments λ€λ μ€μ λμ΄ μμ
- [μ°Έκ³ λ§ν¬](http://youngest-programming.tistory.com/332)
- [anim-μ°Έκ³ λ§ν¬](http://youngest-programming.tistory.com/483)


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



- HomeViewPagerFragment(μμ Plant List νλκ·Έλ¨ΌνΈ) β PlantDetailFragment(μλ¬Ό μμΈ νλ©΄) λ₯Ό λ³΄λ©΄,,

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

- **PlantDetailFragment**λ plantIdλΌλ String νμ λ§€κ°λ³μλ₯Ό μ λ¬ λ°λλ€.



- κ·Έλ¦¬κ³  **HomeViewPager**λ `<action>`μΌλ‘ PlantDetailFragmentλ‘ Directionμ΄ μ€μ λμ΄ μλλ°,
- μ΄λ κ² μ€μ νλ©΄ λΉλ μ μλμΌλ‘ NavControllerμμ λ€λΉκ²μ΄μ νλ ν¨μκ° λ§λ€μ΄μ§λ€!

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



- PlantListFragment  λ¦¬μΈμ΄ν΄λ¬λ·°μ **PlantAdapter**μ μμ΄ν ν΄λ¦­ μ PlantDetailFragmentλ‘ μ΄λνλ μ΄λ²€νΈκ° κ΅¬νλμ΄ μλ€.

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



- **PlantDetailFragment**μμλ by navArgsλ‘ κ°μ μ λ¬λ°λλ€!

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
- MVVMμμ Viewμ ν΄λΉνλ Activity, Fragment, Adapter
- μ΄ νλ‘μ νΈλ SPA(Single-Page-Application) κ΅¬μ‘°!

**[Garden Activity]**
- μ μΌν ActivityμΈ GardenActivity
- νλκ·Έλ¨ΌνΈμ κ»λ°κΈ° μ­ν μ ν¨
```kotlin
@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
    }
}
```

- Jetpack Navigationμ νμ©νκΈ° μν μ΅μλ€κ³Ό, FragmentCotainerViewλ₯Ό ν΅ν΄ νλκ·Έλ¨ΌνΈλ₯Ό λ΄λλ€!
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
- μ¬κΈ°μ ν­κ³Ό λ·°νμ΄μ λ₯Ό λΆμΈλ€!
- SunflowerPagerAdapterμμ λ·°νμ΄μ 2 μμ λ€μ΄ κ° fragmentλ₯Ό invoke
<img src="https://user-images.githubusercontent.com/85485290/161228972-944d4ecc-239f-438f-b044-4a9da788ff8c.png" width="200">

**[SunflowerPagerAdpater]**

> β¨ createFragmentμ λ‘μ§ μ°Έκ³ 
- 1λ² GardenFragment / 2λ² PlantListFragmentλ₯Ό create
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

- ListAdpaterλ₯Ό μμλ°μμ **DiffUtil**μ μ μ©νλ€!

πΒ μ°Έκ³  λ§ν¬

[[Kotlin Android] RecyclerView μ΄λν°μ λ°μ΄ν° λΉ λ₯΄κ² λ°κΎΈκΈ° - ListAdapterμ DiffUtil μ¬μ©νκΈ°](https://velog.io/@l2hyunwoo/Android-RecyclerView-DiffUtil-ListAdapter)

[](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding#0)

[](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter)

**Adapterμμ notifyDataSetChanged() λ₯Ό μ°λ©΄ νλ (?) μ΄μ **

RecyclerViewμμ μ΄λν°κ° νλ μΌμ

- λ―Έλ¦¬ μμ±ν΄ λ λ·°νλ κ°μ²΄μ
- μ¬μ©μκ° μνλ λ°μ΄ν° λ¦¬μ€νΈλ₯Ό μ£Όμνκ³ 
- μ΄ λ°μ΄ν° λ¦¬μ€νΈμ λ³κ²½μ¬ν­μ΄ μμ λ μ΄λ₯Ό UIμ λ°μ

νλ κ²μΈλ°,

μ΄λν°μμ notifyDataSetChanged()λ₯Ό μ¬μ©νλ©΄

> λ¦¬μ€νΈ μμ λ°μ΄ν°κ° λ°λμμΌλκΉ μ μ²΄ λ¦¬μ€νΈ λ€ λ°κΏ!
> 

κ° λλ²λ¦¬λ κ²

κ·Έλ°λ° λ§μ½μ ν λ²μ λ°μ Itemλ€μ΄ 100~200κ° μ΄μμ΄λΌλ©΄ ν΅μ§Έλ‘ μλ°μ΄νΈν  λ μ§μ° μκ°μ΄ λΉμ°ν λ°μν  μλ°μ μμκ³ , κ·Έλμ RecyclerViewμμλ μ§μμ μΌλ‘ Flickering Issueκ° μμ΄μμ

κ·Έλμ μ΄λ° λΉμ©μ μ€μ΄κ³ μ Android μ§μμμ λ΄λμ ν΄κ²°μ±μ **DiffUtil.**

#### DiffUtil

RecyclerView.DiffUtilμ

- νμ¬ λ°μ΄ν° λ¦¬μ€νΈμ κ΅μ²΄λ  λ°μ΄ν° λ¦¬μ€νΈλ₯Ό λΉκ΅νκ³ 
- μ§μ§ λ°λμ΄μΌ ν  λ°μ΄ν°λ§ λ°κΏμ€μΌλ‘μ¨
- *notify λ­μκΈ°*Β λ³΄λ€ ν¨μ¬ λΉ λ₯Έ μκ° λ΄μ λ°μ΄ν° κ΅νμ ν  μ μκ² νλ€!

ListAdapterλ DiffUtilμ νμ©νμ¬ λ¦¬μ€νΈλ₯Ό μλ°μ΄νΈν  μ μλ κΈ°λ₯μ μΆκ°ν AdapterλΌκ³  μκ°νλ©΄ λ¨.

κΈ°μ‘΄ μ΄λν°μ λΉκ΅ν΄μ μΆκ°λ‘ DiffUtil κΈ°λ₯μ λν μ½λ°± κΈ°λ₯ ν΄λμ€λ§ κ΅¬ννλ©΄ λλ―λ‘ μμ°μ±, ν¨μ¨μ±μ λμΌ μ μμ λ―

DirffUitlμ μ¬μ©νκΈ° μν΄μλ ν΄λΌμμ DiffUtil.CallbackλΌλ κΈ°λ₯μ κ΅¬νν΄μΌ ν¨

- `areItemsTheSame`Β : μ΄μ  μ΄λν°μ λ°λλ μ΄λν°μ μμ΄νμ΄ λμΌν μ§ νμΈν¨. κ° μμ΄νμ κ³ μ  IDκ°(DBμμ key κ°μ κ²μ λ°μμ¨λ€λ©΄ κ·Έκ±Έ νμ©)μ΄ μμνλ° μ΄κ±Έ νμ©νμ¬ λΉκ΅νλ©΄ λ¨
- `areContentsTheSame`Β : μ΄μ  μ΄λν°μ λ°λλ μ΄λν°μ μμ΄ν λ΄ λ΄μ©μ λΉκ΅νλ€.Β `areItemsTheSame`Β μμ trueκ° λμ¬ κ²½μ° μΆκ°μ μΌλ‘ λΉκ΅νκΈ° μν΄μ μ¬μ©νλ ν¨μ.

λ§μ½ μ¬κΈ°μΒ `areItemsTheSame`Β λ₯Ό μλͺ» μ μνλ©΄ λͺ¨λ  Itemμ λ€ μ§μ°κ³  μ μμ΄νμ Insertνλ μΌμ΄ μ΄λλμ΄μ notifyDataSetChanged()μ λ³λ° λ€λ₯Ό λ° μμ΄μ§ μκ° μμΌλ―λ‘,,,μ£Όμ!

- PlantAdapterμμ κ΅¬νν λͺ¨μ΅

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

- λ¦¬μ€νΈμ΄λν°λ λ€μκ³Ό κ°μ ννλ₯Ό μ§λ

`ListAdapter<λ°μ΄ν° ν΄λμ€, λ¦¬μ¬μ΄ν΄λ¬λ·° λ·°νλ>(κ°λ°μ μ μ μ½λ°±)`

- λ°μ΄ν° ν΄λμ€λ₯Ό λ°λκ² νΉμ§
- μ¬μ©μκ° μ΄λν° λ΄μμ λ¦¬μ€νΈλ₯Ό μ μνμ§ μκ³  λ¦¬μ€νΈ μμ²΄μμ λ°μ΄ν° λ¦¬μ€νΈλ₯Ό μ μνκΈ° λλ¬Έ!

λ¦¬μ€νΈ μ΄λν°μ μ£Όμ λ©μλ

- `getItem(position: Int)`Β : protected methodλΌ ν΄λμ€ λ΄λΆμμ κ΅¬νν  λ μ¬μ©. μ΄λν° λ΄ List Indexingμ ν  λ νμ©λ¨
- `getCurrentList()`Β : μ΄λν°κ° κ°μ§κ³  μλ λ¦¬μ€νΈλ₯Ό κ°μ Έμ¬ λ μ¬μ©ν¨.
- `submitList(MutableList<T> list)`Β : λ¦¬μ€νΈ ν­λͺ©μ λ³κ²½νκ³  μΆμ λ μ¬μ©ν¨β¨

- PlantListFragmentμμ λ€μκ³Ό κ°μ΄ viewmodelμ λ°μ΄ν°μ λ³κ²½μ΄ μμΌλ©΄ submitListλ‘ λ¦¬μ€νΈ ν­λͺ©μ λ°κΏμ€!

```kotlin
private fun subscribeUi(adapter: PlantAdapter) {
    viewModel.plants.observe(viewLifecycleOwner){plants->
adapter.submitList(plants)
}
}
```

**[PlantDetailFragment]**

- PlantAdapter μμμ μμ΄ν νλλ₯Ό λλ₯΄λ©΄ PlantDetailFragmentλ‘ μ΄λνλ κ²μ λ³Ό μ μμ

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


- μ€ν¬λ‘€μ λ°λΌ νλ©΄μ ν΄λ°(μ±λ°)κ° μ νκ³  νΌμ³μ§λ μ λλ©μ΄μμ κ°μ§λ CollapsingToolbarLayoutμ΄ μ¬μ©λμμ

<img src="https://user-images.githubusercontent.com/85485290/161272969-ad598d0a-ffe9-4cea-987b-b00a04420ecf.png" width="300">

μ κ΅¬μ‘°λ‘ λμ΄μμ!


πΒ CollapsingToolbar μ°Έκ³  λ§ν¬

[CollapsingToolbar μμ 1](https://youngest-programming.tistory.com/353)

[CollapsingToolbar μμ 2](https://youngest-programming.tistory.com/380)

- +λ²νΌ λͺ¨μμ λλ₯΄λ©΄ ViewModelμ addPlantToGarden()μ νΈμΆ ν΄ μλ¬Όμ΄ My Gardenμ μ μ₯λκ³  μ€λ΅λ° λ©μΈμ§λ₯Ό λμ΄λ€. κ·Έλ¦¬κ³  + λ²νΌμ μ¬λΌμ§λ€!

```kotlin
plant?.let {
                    hideAppBarFab(fab)
                    plantDetailViewModel.addPlantToGarden()
                    Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
```

- [PlantDetailViewModel]μ μ κΉ λ³΄λ©΄ λ€μκ³Ό κ°λ€

```kotlin
fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }
```

- ν΄λ° μ€λ₯Έμͺ½ λμ κ³΅μ  λ²νΌμ λλ₯΄λ©΄ **createShareIntent()**κ° νΈμΆλμ΄ κ³΅μ νκΈ° κΈ°λ₯μ΄ μ€νλλ€.

<img src="https://user-images.githubusercontent.com/85485290/161273833-bb6343d0-923a-4841-87fb-efe5167c0c24.png" width="300">

**[GardenFragment]**

<img src="https://user-images.githubusercontent.com/85485290/161273964-7b472817-953b-4638-a71f-47c973015e7c.png" width="300">

- hasPlantingsμ κ°μ λ°λΌ λ³΄μ΄λ λ·°κ° λ€λ¦ β λ°μ΄ν° λ°μΈλ©μΌλ‘ μ²λ¦¬

<img src="https://user-images.githubusercontent.com/85485290/161274034-a8cc6ec2-e307-4444-aa4c-4947aee93c52.png" width="400">

- BindingAdapters.ktμ μ μλμ΄ μλ app:isGone

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

- GardenFragmentμμ λ°μ΄ν° λ¦¬μ€νΈκ° λΉμλμ§, μλΉμλμ§ μ²λ¦¬ν  μ μμ!

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

πΒ μ°Έκ³ λ§ν¬

- flow

[stateflow-and-sharedflow](http://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

[flow-μ λ¦¬κΈ](http://yoon-dailylife.tistory.com/72)

- coroutine

[architecture-with-coroutine](http://developer.android.com/topic/libraries/architecture/coroutines?hl=ko)

[coroutine](http://developer.android.com/kotlin/coroutines?hl=ko)



- μ μΌν μ‘ν°λΉν°μΈ GardenActivityμ ViewModelμ μλ€.


**[PlainListViewModel]**

<img src="https://user-images.githubusercontent.com/85485290/161577629-c90b2f8c-9d45-43b7-9440-e395f7b2d4d9.png" width="300">


- μμ±μλ‘ MVVMμμ `Model`μ ν΄λΉνλ PlantRepositoryμ SavedStateHandleμ κ°μ§λ€.

```kotlin
@HiltViewModel
class PlantListViewModel @Inject internal constructor(
    plantRepository: PlantRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
```


- λ°μ΄ν°λ MutableStateFlowλ₯Ό μ¬μ©νλλ°, μνλ₯Ό μλ°μ΄νΈ νκ³  Flowμ μ μ‘νλ κ²μ.
- Flowλ μΌλ°μ μΌλ‘ cold streamμ΄μ§λ§, StateFlowλ `hot stream`μ΄λ€. μΌλ° Flowλ λ§μ§λ§ κ°μ κ°λμ΄ μκ³  collect λ  λλ§ νμ±ν λλ λ°λ©΄, StateFlowλ **λ§μ§λ§ κ°**μ κ°λμ΄ μμΌλ©° **μμ±νμλ§μ νμ±ν** λλ€κ³  ν¨!!
- Flowλ Coldλ Subject β StateFlowλ Hotμ΄λ Observable
- Streamνκ³  λ°μ΄ν°λ₯Ό μ μ₯νλ€λ κΈ°λ₯μΌλ‘ κΈ°μ‘΄μ **LiveData + RxJava λλ,,??**
- LiveDataμ Flowμ μ°¨μ΄μ  μ€ νλκ° Flowλ flatMapLatestμ κ°μ Stream ν¨μλ₯Ό μ κ³΅νλ€λ μ !


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

- ViewModelScopeλ μ±μ κ° ViewModelμ λμμΌλ‘ μ μλλ€. λ¬Έμλ₯Ό μΈμ©ν΄λ³΄μλ©΄,,


> μ΄ λ²μμμ μμλ λͺ¨λ  μ½λ£¨ν΄μΒ ViewModelμ΄ μ­μ λλ©΄ μλμΌλ‘ μ·¨μλ©λλ€. μ½λ£¨ν΄μΒ ViewModelμ΄ νμ± μνμΈ κ²½μ°μλ§ μ€νν΄μΌ ν  μμμ΄ μμ λ μ μ©ν©λλ€. μλ₯Ό λ€μ΄ λ μ΄μμμ μΌλΆ λ°μ΄ν°λ₯Ό κ³μ°νλ€λ©΄ μμμ λ²μλ₯ΌΒ ViewModelλ‘ μ§μ νμ¬Β ViewModelμ μ­μ νλ©΄ λ¦¬μμ€λ₯Ό μλͺ¨νμ§ μλλ‘ μμμ΄ μλμΌλ‘ μ·¨μλ©λλ€.
> 


λΌκ³  νλΉ

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
StateFlowμ MutableStateFlow!

<img src="https://user-images.githubusercontent.com/85485290/161577833-15652cb0-8932-42aa-98e8-c3cdfbfddc76.png" width="400">
LiveDataμ Flowμ μ°¨μ΄μ ?

---
### 4. Model


