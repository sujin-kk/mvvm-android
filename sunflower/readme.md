## 목차
- [1. MVVM](#1-mvvm)
- [2. MVVM ViewModel vs AAC ViewModel](#2-mvvm-viewmodel-vs-aac-viewmodel)
- [3. ViewModel의 사용](#3-viewmodel의-사용)
- [4. LiveData](#4-livedata)
- [5. DataBinding](#5-databinding)
- [6. Coroutine](#6-coroutine)

---
### 1. MVVM
<img width="600" alt="image" src="https://user-images.githubusercontent.com/85485290/161014604-77f7115a-6998-4f0b-8c24-8305a37bad1c.png">

<img width="600" alt="image" src="https://user-images.githubusercontent.com/85485290/160877526-8d3730f7-24a4-444e-a03c-4c0df3bd4456.png">

🔗 [공식문서](https://developer.android.com/topic/libraries/architecture?hl=ko)

🔗 [MVC vs MVVM](https://velog.io/@haero_kim/Android-%EA%B9%94%EC%8C%88%ED%95%98%EA%B2%8C-MVVM-%ED%8C%A8%ED%84%B4%EA%B3%BC-AAC-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)

- View는 ViewModel의 참조를 가지고 있지만, ViewModel은 View의 참조를 가지고 있지X
- ViewModel은 Model의 참조를 가지고 있지만, Model은 ViewModel의 참조를 가지고 있지X

> 그럼 ViewModel은 어떻게 View의 함수를 호출할 수 있나?
- View가 ViewModel을 Binding 하고 있으면 됨
- ViewModel은 단순히 값을 바꾸기만 하고, View는 그 값이 바뀌는걸 관찰하는 역할


|View|ViewModel|Model|
|------|---|---|
|1. Activity / Fragment가 View의 역할을 함 |1. View가 요청한 데이터를 Model로 요청함|1. ViewModel이 요청한 데이터를 반환함|
|2. 사용자의 Action을 받음(버튼 클릭, 텍스트 입력 등)|2. Model로부터 요청한 데이터를 받음|Room, Realm과 같은 DB 사용이나 Retrofit을 통한 백엔드 API호출(네트워킹 작업)이 보편적|
|3. ViewModel의 데이터를 관찰하여 UI 갱신|||

> MVVM의 장점!
- MVVM은 단방향 의존만 가지게 되는 것이 장점
- View 가 ViewModel 의 Data 를 관찰하고 있으므로 UI 업데이트가 간편
- ViewModel 이 데이터를 홀드하고 있으므로 Memory Leak 발생 가능성 배제
- (View 가 직접 Model 에 접근하지 않아 Activity / Fragment 라이프 사이클에 의존하지 않기 때문)
- 기능별 모듈화가 잘 되어 유지 보수에 용이 (e.g. ViewModel 재사용 및 DB 교체 등의 작업이 편리함)


---
### 2. MVVM ViewModel vs AAC ViewModel
- MVMM 아키텍처 패턴의 ViewModel과 AAC의 ViewModel의 차이

> **MVVM** 패턴 이야기 하면서 구글 이야기를 한다면 자리를 박차고 나가라. - 강사룡님

> **MVVM**은 **AAC**의 **ViewModel**과 연관성이 없다. **AAC ViewModel**을 전제로 **MVVM**을 설명하려고 한다면 단언컨데 우리 회사 1차 면접도 통과하지 못할 것이다 - 정승욱님

- [[Android]MVVM ViewModel과 AAC ViewModel의 차이](https://medium.com/kenneth-android/android-mvvm-viewmodel%EA%B3%BC-aac-viewmodel%EC%9D%98-%EC%B0%A8%EC%9D%B4-8c0d54922e07)

- [MVVM ViewModel vs AAC ViewModel](https://www.notion.so/MVVM-ViewModel-vs-AAC-ViewModel-ef453da743a54f3b9efa3d4c6b9e38aa)

<img width="600" alt="image" src="https://user-images.githubusercontent.com/85485290/161016510-4ea12552-2631-4099-8cc2-37ec8bf7f46f.png">

- MVVM ViewModel
> View와 Model 사이에서 데이터를 관리해주고 바인딩 해주는 역할(매개체 역할)

> 비즈니스 로직과 프레젠테이션 로직을 UI로부터 분리하는 것을 목표로 함

- AAC ViewModel
> AAC의 ViewModel은 화면 전환과 같은 환경에서 데이터를 보관하고, 라이프사이클을 알고 있어서 Activity나 Fragment의 Destory시 onClear함수를 통한 데이터 해제의 역할을 하는 Adnroid JetPack 라이브러리


> 구글에서 ViewModel의 목적을 데이터 관리 및 바인딩이라고 만든 것이 아님!!


---
### 3. ViewModel의 사용
[참고링크](https://readystory.tistory.com/176)

#### 1. 파라미터가 없는 ViewModel - Lifecycle Extensions

- 가장 편한 방법중 하나, androidx.lifecycle의 모듈을 가져와서 사용하면 됨

- step1. module 수준의 build.gradle에 디펜던시 추가
```
dependencies {
    // ...
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
}
```

- step2. ViewModel 클래스 정의
```kotlin
  class NoParamViewModel : ViewModel()
```

- step3. 액티비티에서 뷰모델 객체 생성
```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var noParamViewModel: NoParamViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        /* use ViewModelProvider's constructor provided from lifecycle-extensions package */
        noParamViewModel = ViewModelProvider(this).get(NoParamViewModel::class.java)
    }
}
//this는 ViewModelStoreOwner 타입인데 액티비티, 프래그먼트 사용가능
```

#### 2. 파라미터가 없는 ViewModel - ViewModelProvider.NewInstanceFactory

- NewInstanceFactory는 안드로이드가 기본적으로 제공해주는 팩토리 클래스, ViewModelProvider.Factory 인터페이스를 구현하고 있음. 따라서 ViewModel 클래스가 파라미터를 필요로 하지 않거나, 특별히 팩토리를 커스텀 할 필요가 없는 상황에서는 1번 방법을 사용하거나, 2번 방법을 사용하면 되겠습니다. (커스텀 안할거면 1번쓰기?)

```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var noParamViewModel: NoParamViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        noParamViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(NoParamViewModel::class.java)
    }
}
```

#### 3. 파라미터가 없는 ViewModel - ViewModelProvider.Factory

- 팩토리를 직접 구현하는 방법인데 장점은 하나의 팩토리로 다양한 ViewModel 클래스를 관리할 수도 있고, 원치 않는 상황에 대해서 컨트롤 할 수 있다.

```kotlin
class NoParamViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NoParamViewModel::class.java)) {
            NoParamViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
```

<aside>
💡 위 코드는 NoParamViewModel 클래스가 아니면 IllegalArgumentException 을 던지도록 구현되어 있다. 이는 어디까지나 개발자의 마음대로 구현하면 되는 부분이며, 어떤 타입의 클래스가 전달되더라도 인스턴스를 생성하도록 구현할 수도 있다고함.
</aside>

#### 4. 파라미터가 있는 ViewModel - ViewModelProvider.Factory(3번의 연장선)

- **ViewModelProvider.Factory** 를 구현하면 파라미터를 소유하고 있는 ViewModel 객체의 인스턴스를 생성할 수 있다. 직접 구현한 Factory 클래스에 파라미터를 넘겨주어 create() 내에서 인스턴스를 생성할 때 활용하면 된다.


- step1. ViewModel 작성
```kotlin
class HasParamViewModel(val param: String) : ViewModel()
```

- step2. Factory 구현
```kotlin
class HasParamViewModelFactory(private val param: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HasParamViewModel::class.java)) {
            HasParamViewModel(param) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
```

- step3. Acitivty에서 사용
```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var hasParamViewModel: HasParamViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        val sampleParam = "Ready Story"
 
        hasParamViewModel = ViewModelProvider(this, HasParamViewModelFactory(sampleParam))
            .get(HasParamViewModel::class.java)
    }
}
// 3,4 번에서는 파라미터 유무에 따라 별도의 팩토리 클래스를 구현하였지만, 꼭 그럴 필요 없이 하나의 팩토리 클래스로 두가지 상황을 한꺼번에 처리 가능!
```

#### 5. 파라미터가 없는 AndroidViewModel - AndroidViewModelFactory

- 사실 developer 사이트에 의하면 ViewModel 클래스에서 Context 객체를 소유하거나 접근하는 것에 있어서 권장하지 않고 있다. 하지만 정말 불가피하게 필요한 경우가 있을 수 있는데, ViewModel 에서 Context 를 사용해야할 필요성이 있을 때는 **AndroidViewModel** 클래스를 사용하면 됨.
- 안드로이드에서는 이러한 AndroidViewModel 객체에 대한 생성을 위해 ViewModelProvider.AndroidViewModelFactory 라는 별도의 팩토리를 제공!

step1. ViewModel 작성
```kotlin
class NoParamAndroidViewModel(application: Application) : AndroidViewModel(application)
```

step2. AndroidViewModel 은 Application 객체를 필요로함.
이번에는 AndroidViewModelFactory 를 이용하여 뷰모델 객체를 생성한다!
```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var noParamAndroidViewModel: NoParamAndroidViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        noParamAndroidViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(NoParamAndroidViewModel::class.java)
    }
}

//AndroidViewModelFactory는 내부에 NewInstanceFactory를 상속했다.
```

#### 6. 파라미터가 있는 AndroidViewModel

- **파라미터가 있는** **AndroidViewModel** 객체를 생성하는 방법이다.
- 사실 4번의 방법으로도 가능하지만, 5번에서 살펴본 AndroidViewModelFactory 와 유사한 방식으로 커스텀 팩토리를 구현해보는 방법이다.

- step1. 먼저 파라미터가 있는 AndroidViewModel 클래스를 준비한다.
```kotlin
class HasParamAndroidViewModel(application: Application, val param: String)
    : AndroidViewModel(application)
```

- step2. Custom Factory를 구현
```kotlin
class HasParamAndroidViewModelFactory(private val application: Application, private val param: String)
    : ViewModelProvider.NewInstanceFactory() {//ViewModelProvider.Factory로 해도 된다고 한다.
 
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(Application::class.java, String::class.java)
                    .newInstance(application, param)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
        return super.create(modelClass)
    }
}
```

<aside>
💡 **android-ktx / fragment-ktx 모듈을 사용하면 보다 편리하게 뷰모델 인스턴스를 생성할 수도 있다고 한다.**
</aside>

---
### 4. LiveData

---
### 5. DataBinding

```
dataBinding {
    enabled = true
}
```
---
### 6. Coroutine

