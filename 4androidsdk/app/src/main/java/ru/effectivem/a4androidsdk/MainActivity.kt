package ru.effectivem.a4androidsdk

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import ru.effectivem.a4androidsdk.databinding.ActivityMainBinding
import ru.effectivem.a4androidsdk.z1Fragments.MyNavigationRouter
import ru.effectivem.a4androidsdk.z1Fragments.NavigationRouter
import ru.effectivem.a4androidsdk.z1Fragments.fragments.FirstFragment
import ru.effectivem.a4androidsdk.z1Fragments.fragments.SecondFragment
import ru.effectivem.a4androidsdk.z1Fragments.fragments.ThirdFragment
import ru.effectivem.a4androidsdk.z2workManager.MyBatteryWorkManager
import ru.effectivem.a4androidsdk.z4chat.ui.ChatFragment

class MainActivity : AppCompatActivity() {

    private val fragmentPosition = "fragment_position"

    private lateinit var binding: ActivityMainBinding
    private lateinit var router: NavigationRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRouter(savedInstanceState?.getInt(fragmentPosition))
        initUI(savedInstanceState)
        setupWorker(this)
    }

    private fun setupRouter(startFragmentPosition: Int?) {
        with(binding) {
            router = MyNavigationRouter(
                fragmentManager = supportFragmentManager,
                containerViewId = fragmentContainerView.id,
                startPosition = startFragmentPosition,
                fragments = listOf(
                    FirstFragment(),
                    SecondFragment(),
                    ThirdFragment(),
                )
            )
        }
    }

    private fun initUI(savedInstanceState: Bundle?) {
        with(binding) {
            backBtn.setOnClickListener {
                router.openPrevious()
            }
            nextBtn.setOnClickListener {
                router.openNext()
            }
            toChatBtn.setOnClickListener {
                router.replace(ChatFragment())
            }

            if (savedInstanceState == null) {
                toChatBtn.viewTreeObserver.addOnGlobalLayoutListener(
                    object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            toChatBtn.viewTreeObserver.removeOnGlobalLayoutListener(this)

                            toChatBtn.visibility = View.VISIBLE
                            toChatBtn.alpha = 0f
                            toChatBtn.animate()
                                .alpha(1f)
                                .setDuration(1000)
                                .start()
                        }
                    }
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (router.currentPosition != 0) outState.putInt(fragmentPosition, router.currentPosition)
    }

    private fun setupWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val request = OneTimeWorkRequestBuilder<MyBatteryWorkManager>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(request)

    }
}