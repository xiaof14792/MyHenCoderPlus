package com.example.threadrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Timed
import kotlinx.android.synthetic.main.activity_main.*
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import kotlin.math.sin

/**
 * 1.创建Single对象的原理
 * 2.map()操作符的原理
 * 3.Disposable的原理
 *  分几种情况：1）无上级（新创建的）
 *                  无延迟、无后续 -不用取消
 *                  有延迟或有后续 -通过IntervalObserver(实现了disposable、runnable接口，两个作用：既是定时器执行任务也对外提供取消的统一接口),最终取消定时器
 *
 *            2) 有上级
 *                  无延迟、无后续 -SingleMap,直接把上游传递的disposable对象调用下游的onSubscribe方法，传给下游Observer，因为它没有延迟、后续，只要上游不生产它也不会往下传数据
 *                  有延迟、无后续
 *                  无延迟、有后续
 *                  有延迟、有后续
 *
 *            关键点：是取消父Source停止生产事件，还是取消自己（中间的装饰者Observer子类）生产延时事件（delay interval），还是两者都取消，还是两者都不取消
 * 4.subscribeOn的原理
 * 5.observeOn的原理
 * 6.Scheduler切换线程的原理
 */
class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val single: Single<Int> = Single.just(2)
        val singleString = single.map(object : Function<Int, String> {
            override fun apply(t: Int?): String {
                return t.toString()
            }

        })

        singleString.subscribe(object : SingleObserver<String> {
            override fun onSuccess(t: String?) {
                textView.text = t
            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onError(e: Throwable?) {
                textView.text = e.toString()
            }

        })*/

        //无上级，有后续有延迟
        /*Observable.interval(0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: Long?) {
                    textView.text = t.toString()
                }

                override fun onError(e: Throwable?) {

                }

            })*/

        //有上级，无延迟、无后续
        /*val single = Single.just(1)
        single.map(object : Function<Int, String>{
            override fun apply(t: Int?): String {
                return t.toString()
            }

        }).subscribe(object : Consumer<String>{
            override fun accept(t: String?) {
                println(t)
            }

        })*/

        //有上级，有延迟、无后续 -分两个阶段，上游调用onSubscribe时，传递的时上游传下来的Disposable，延迟发数据后replace为自己的定时器，取消延时任务
        /*val singleDelay = Single.just(1)
            .delay(2, TimeUnit.SECONDS)
            .subscribe(object : SingleObserver<Int> {
                override fun onSuccess(t: Int?) {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onError(e: Throwable?) {
                }

            })*/

        //有上级，无延迟、有后续 -ObservableMap,其实还是把上游传递的Disposable对象保存到中间类的upstream变量里，实际dispose方法时调用上游Disposable对象的dispose方法
        /*val observableMap = Observable.just(1, 2)
            .map(object : Function<Int, String> {
                override fun apply(t: Int?): String {
                    return t.toString()
                }

            }).subscribe(object : Observer<String> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }


            })*/

        //有上级，有延迟、有后续 -ObservableMap, 把上游传递的disposable对象作为一个变量保存在中间装饰类里，调用下游observer的onSubscribe方法时传递参数为this，调用自己的dispose()方法时做两件事
        //取消定时任务、调用上游disposable对象的dispose方法让上游停止生产数据
        /*Observable.interval(1, 1, TimeUnit.SECONDS)
            .map(object : Function<Long, String> {
                override fun apply(t: Long?): String {
                    return t.toString()
                }

            }).subscribe(object : Observer<String> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }

            })*/

        //observeOn、subscribeOn方法原理
        /*Single.just(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Int> {
                override fun onSuccess(t: Int?) {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onError(e: Throwable?) {
                }

            })*/

        //有后续的情况
        /*Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: Long?) {
                }

                override fun onError(e: Throwable?) {
                }

            })*/

        //Scheduler 切换线程的原理 -io()是基于newThread()，createWorker，使用创建的worker来scheduleDirect(runnable)来切换线程执行run方法，
        // NewThreadWorker内部包有ExecutorService，实际使用的是Java的Executor来切换线程；io()基于NewThread的NewThreadWorker，只不过加上了ExecutorPool对线程池加上了复用机制

        //AndroidSchedulers.mainThread()是一个实现Scheduler接口的类，构造函数里有Handler对象，handler构造函数Looper.getMainLooper()，使用了跟main线程绑定
        //的handler对象将runnable的message发送到主线程去执行。
        Single.just(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<Int> { println(it.toString()) })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}