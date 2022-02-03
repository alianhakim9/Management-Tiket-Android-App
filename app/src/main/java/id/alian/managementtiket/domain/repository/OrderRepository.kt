package id.alian.managementtiket.domain.repository

import id.alian.managementtiket.data.remote.dto.OrdersDto

interface OrderRepository {
    suspend fun getOrders(): List<OrdersDto>
}