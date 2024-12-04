package com.mjc.lst1995.farmhelper.core.domain.usecase

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.InetAddress
import javax.inject.Inject

class NetworkUseCase
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
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
