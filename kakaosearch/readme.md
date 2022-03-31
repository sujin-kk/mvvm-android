### MVVM 패턴을 적용한 카카오 API 이미지 검색 앱

<img width="250" alt="image" src="https://user-images.githubusercontent.com/85485290/160876673-58089470-2d17-4d37-821c-1d1c0519107d.png">

---
### 1. MVVM
#### ViewModel
- ViewModel이 상속 받을 BaseViewModel
- Model에서 들어오는 Single<>과 같은 RxJava 객체들의 Observing을 위한 부분
- 기본적으로 RxJava의 Observable들은 compositeDisposable에 추가를 해주고, 뷰모델이 없어질 때 추가했던 것들을 지워줘야 함
- 이 부분은 그러한 동작을 수행하는 코드로서, Observable들을 옵저빙할때 addDisposable()을 사용!
- 또한 ViewModel은 View와의 생명주기를 공유하기 때문에 View가 부서질 때 ViewModel의 onCleared()가 호출되게 되며, 그에 따라 옵저버블들이 전부 클리어 된다.

```kotlin
open class BaseKotlinViewModel : ViewModel() {

    /**
     * RxJava 의 observing을 위한 부분.
     * addDisposable을 이용하여 추가하기만 하면 된다
     */
    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
```

#### View
- 모든 Activity가 상속 받을 BaseActivity

```kotlin
/**
 * BaseKotlinActivity<ActivityMainBinding>
 * 와 같이 상속 받을 때, ActivitySbsMainBinding 과 같은 파일이 자동생성되지 않는다면
 * 1. 해당 엑티비티의 레이아웃이 <layout></layout> 으로 감싸져 있는지 확인
 * 2. 다시 빌드 수행 or 클린 빌드 후 다시 빌드 수행
 * 3. 이름 확인 : lim_main_activity => ActivityLimMainBinding
 */
abstract class BaseKotlinActivity<T : ViewDataBinding, R : BaseKotlinViewModel> : AppCompatActivity() {

    lateinit var viewDataBinding: T

    /**
     * setContentView로 호출할 Layout의 리소스 Id.
     * ex) R.layout.activity_main
     */
    abstract val layoutResourceId: Int

    /**
     * viewModel 로 쓰일 변수.
     */
    abstract val viewModel: R

    /**
     * 레이아웃을 띄운 직후 호출.
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰..
     */
    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    abstract fun initAfterBinding()

    private var isSetBackButtonValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }
}
```

- 일반적인 레이아웃을 만들되, 그 레이아웃 전체를 <layout>태그로 감싸주기

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto">

    <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".view.MainActivity">
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
  
---
### 2. DI(Dependency Injection)

- 의존성 주입
> 김OO : A에서 B가 필요한데 (A가 B를 의존함), 이 B를 누가 줌,,, (명쾌하다!)
 
ex) 현재 MVMM 패턴에서 뷰모델을 테스팅한다고 치면, 뷰모델이 의존하고 있는 모델을 어떻게 만들어 놔야 할까?
간단한 방법으로는 모델을 내부에서 생성하지 않고, 생성자의 인자로 줄 수 있음!

```
  // model을 내부 생성?
  ViewModel {
    val model = Model()
  }
  
  // model을 인자로 주입
  ViewModel(val model) {
  }
```
> 테스팅용 모델을 뷰모델의 생성자로 '주입' 해줌으로써 뷰모델을 테스팅 할 수 있다.
근데 이걸 일일이 모델을 생성해준다면,, 꽤 불편할 것 같은데,,

의존성 주입을 도와주는 대표적 라이브러리 2가지
-   Dagger2
-   Koin
  
---
### 3. KOIN
  
🔗[Koin 공식홈페이지](https://insert-koin.io/)

🔗[Koin 깃허브](https://github.com/InsertKoinIO/koin)
  
- step1. 디펜던시 추가
```kotlin
  implementation "org.koin:koin-androidx-scope:1.0.2"
  implementation "org.koin:koin-androidx-viewmodel:1.0.2"
```

- step2. 의존성 주입에 사용할 모듈 짜기
 
```kotlin
  var modelPart = module {
    factory<DataModel> {
        DataModelImpl()
    }
  }

  var viewModelPart = module {
      viewModel {
          MainViewModel(get())
     }
  }

  var myDiModule = listOf(modelPart, viewModelPart)
```

- factory { }
-- DataModelImpl() 이라는 클래스를 뚝딱뚝딱 만들어주는 공장
-- 다른 클래스에서 이 부분이 필요하다면 get()을 해주면 팩토리로 만든 클래스가 쏙 들어감
-- 팩토리외에도 single{}이 있는데, 이건 싱글톤패턴처럼 어플리케이션에서 단 하나만 만듦
-- 보통 Retrofit을 통해 만든 서비스 클래스를 single로 만든다고 함
--  get()으로 객체를 주입하는 것 이외에도, by inject()로 얻을수도 있음!

- viewModel { }
--  뷰모델을 만듦
-- 액티비티에서 by viewModel()을 통해서 얻어올 수 있음
  
- step3. Application()을 상속받아 startKoin 해주기  
```kotlin
  class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, myDiModule)
    }
}
```

- Application 클래스를 상속한 뒤 메니페스트 파일에서 name 설정하는 것 잊지않기
```xml
  <application
        android:name=".MyApplication"
```
- step4. MainActivity에서 viewModel 주입
```koltin
  override val viewModel: MainViewModel by viewModel()
```

- 주입 예제
1.
```kotlin
   val service : BusinessService by inject()
```
               
2.
```kotlin
   class Controller(val service : BusinessService){ 
  
  fun hello() {
     // service is ready to use
     service.sayHello()
  }
} 
```
               
3.
```kotlin
     val vm : MyViewModel by viewModel()
```
               
- 추가) 테스팅 예제
```kotlin
// Just tag your class with KoinTest to unlick your testing power
class SimpleTest : KoinTest { 
  
  // lazy inject BusinessService into property
  val service : BusinessService by inject()

  @Test
  fun myTest() {
      // You can start your Koin configuration
      startKoin(myModules)

      // or directly get any instance
      val service : BusinessService = get()

      // Don't forget to close it at the end
      stopKoin()
  }
} 
```
             
### 4. RxJava
