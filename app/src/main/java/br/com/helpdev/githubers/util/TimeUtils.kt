package br.com.helpdev.githubers.util

import java.util.concurrent.TimeUnit

fun isLessThanOneHour(timeInMillis: Long) = (System.currentTimeMillis() - timeInMillis) < TimeUnit.HOURS.toMillis(1)
