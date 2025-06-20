package com.example.booklibrary.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class JobSplash {
    private var progress = 0
    private var max = 100
    private var delay = 80L

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var loopingFlowJob: Job? = null
    private var loopingFlow = flow<Unit> {
        while(true) {
            emit(Unit)
            delay(delay)
        }
    }

    private var jobProgress: ProgressUpdated? = null

    fun startJob(listener: ProgressUpdated?) {
        jobProgress = listener
        stopJob()

        if(isProgressMax()) {
            jobProgress?.onProgressUpdate(progress)
            return
        }

        loopingFlowJob = coroutineScope.launch(Dispatchers.Main) {
            loopingFlow.collect {
                progress++
                jobProgress?.onProgressUpdate(progress)
                if(isProgressMax()) stopJob()
            }
        }
    }

    fun stopJob() {
        loopingFlowJob?.cancel()
        loopingFlowJob = null
    }

    fun isProgressMax(): Boolean{
        return progress >= max
    }

    fun destroy() {
        stopJob()
        jobProgress = null
    }


    interface ProgressUpdated {
        fun onProgressUpdate(count: Int)
    }
}