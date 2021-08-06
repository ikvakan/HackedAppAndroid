package hr.algebra.hacked.factory

import android.content.Context
import hr.algebra.hacked.dao.HackedSqlHelper

fun getRepository(context: Context?) =HackedSqlHelper(context)