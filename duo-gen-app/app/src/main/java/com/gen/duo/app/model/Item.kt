package com.gen.duo.app.model

import java.math.BigDecimal
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by israjhaliri on 3/8/18.
 */
data class Item constructor(var id: Long?, var title: String, var description: String, var price: BigDecimal, var createDate: String?, var image: String, var viewed: Int?, var booked: Int?, var category: String?) {}