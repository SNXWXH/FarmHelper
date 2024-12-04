package com.mjc.lst1995.farmhelper.core.domain.usecase

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import javax.inject.Inject

class NetworkUseCase
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        suspend fun getPublicIPAddress(): String? =
            withContext(Dispatchers.IO) {
                try {
                    // 외부 서비스를 통한 IP 주소 요청
                    val url = URL("https://api.ipify.org")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000

                    // 응답 확인
                    if (connection.responseCode == 200) {
                        val streamReader = connection.inputStream.bufferedReader()
                        val ipAddress = streamReader.use { it.readText() }
                        streamReader.close()
                        ipAddress // 반환받은 IP 주소
                    } else {
                        "203.0.113.1" // 에러 처리
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    "203.0.113.1"
                }
            }

        fun getDnsServer(): String {
            val dnsServers = mutableListOf<String>()
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val currentNetwork = connectivityManager.activeNetwork
                if (currentNetwork != null) {
                    val linkProperties = connectivityManager.getLinkProperties(currentNetwork)
                    if (linkProperties != null) {
                        dnsServers.addAll(linkProperties.dnsServers.map { it.hostAddress } as Collection<String>)
                    }
                }
            } else {
                // Wi-Fi 상태에서 DHCP 정보를 통해 DNS 서버 주소 가져오기
                @Suppress("DEPRECATION")
                val wifiManager =
                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val dhcpInfo = wifiManager.dhcpInfo
                if (dhcpInfo != null) {
                    val dns1 = intToInetAddress(dhcpInfo.dns1)
                    val dns2 = intToInetAddress(dhcpInfo.dns2)
                    if (dns1 != null) dnsServers.add(dns1.hostAddress)
                    if (dns2 != null) dnsServers.add(dns2.hostAddress)
                }
            }

            return dnsServers.joinToString("")
        }

        private fun intToInetAddress(addr: Int): InetAddress? =
            InetAddress.getByAddress(
                byteArrayOf(
                    (addr and 0xFF).toByte(),
                    (addr shr 8 and 0xFF).toByte(),
                    (addr shr 16 and 0xFF).toByte(),
                    (addr shr 24 and 0xFF).toByte(),
                ),
            )
    }
