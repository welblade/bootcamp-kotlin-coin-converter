package br.com.dio.coinconverter.data.model

enum class Coin(val coinName: String, val iconUrl: String) {
    CHF("Franco Suíço", "https://flagcdn.com/h40/ch.png"),
    FJD("Dólar de Fiji", "https://flagcdn.com/h40/fj.png"),
    MXN("Peso Mexicano", "https://flagcdn.com/h40/mx.png"),
    CLP("Peso Chileno", "https://flagcdn.com/h40/cl.png"),
    DOGE("Dogecoin", "https://i.postimg.cc/d3ckr75w/dodgecoin.png"),
    ZAR("Rand Sul-Africano", "https://flagcdn.com/h40/za.png"),
    VND("Dong Vietnamita", "https://flagcdn.com/h40/vn.png"),
    AUD("Dólar Australiano", "https://flagcdn.com/h40/au.png"),
    ILS("Novo Shekel Israelense", "https://flagcdn.com/h40/il.png"),
    GHS("Cedi Ganês", "https://flagcdn.com/h40/gh.png"),
    BOB("Boliviano", "https://flagcdn.com/h40/bo.png"),
    IDR("Rupia Indonésia", "https://flagcdn.com/h40/id.png"),
    XRP("XRP", "https://flagcdn.com/h40/xr.png"),
    KYD("Dólar das Ilhas Cayman", "https://flagcdn.com/h40/ky.png"),
    TRY("Nova Lira Turca", "https://flagcdn.com/h40/tr.png"),
    IQD("Dinar Iraquiano", "https://flagcdn.com/h40/iq.png"),
    JOD("Dinar Jordaniano", "https://flagcdn.com/h40/jo.png"),
    TWD("Dólar Taiuanês", "https://flagcdn.com/h40/tw.png"),
    HKD("Dólar de Hong Kong", "https://flagcdn.com/h40/hk.png"),
    AED("Dirham dos Emirados", "https://flagcdn.com/h40/ae.png"),
    XAGG("XPrata", "https://i.postimg.cc/X7dMsrxt/prata.png"),
    EUR("Euro", "https://upload.wikimedia.org/wikipedia/commons/b/b7/Flag_of_Europe.svg"),
    DKK("Coroa Dinamarquesa", "https://flagcdn.com/h40/dk.png"),
    CAD("Dólar Canadense", "https://flagcdn.com/h40/ca.png"),
    MYR("Ringgit Malaio", "https://flagcdn.com/h40/my.png"),
    XBR("Brent Spot", "https://cdn-0.emojis.wiki/emoji-pics/lg/oil-drum-lg.png"),
    SYP("Libra Síria", "https://flagcdn.com/h40/sy.png"),
    NOK("Coroa Norueguesa", "https://flagcdn.com/h40/no.png"),
    GEL("Lari Georgiano", "https://flagcdn.com/h40/ge.png"),
    UYU("Peso Uruguaio", "https://flagcdn.com/h40/uy.png"),
    BTC("Bitcoin", "https://i.postimg.cc/G3DvtRXb/bitcoin.png"),
    MAD("Dirham Marroquino", "https://flagcdn.com/h40/ma.png"),
    SEK("Coroa Sueca", "https://flagcdn.com/h40/se.png"),
    LTC("Litecoin", "https://i.postimg.cc/g0B3X292/litecoin.png"),
    UAH("Hryvinia Ucraniana", "https://flagcdn.com/h40/ua.png"),
    BHD("Dinar do Bahrein", "https://flagcdn.com/h40/bh.png"),
    ARS("Peso Argentino", "https://flagcdn.com/h40/ar.png"),
    SAR("Riyal Saudita", "https://flagcdn.com/h40/sa.png"),
    IRR("Rial Iraniano", "https://flagcdn.com/h40/ir.png"),
    AFN("Afghani do Afeganistão", "https://flagcdn.com/h40/af.png"),
    INR("Rúpia Indiana", "https://flagcdn.com/h40/in.png"),
    THB("Baht Tailandês", "https://flagcdn.com/h40/th.png"),
    CNY("Yuan Chinês", "https://flagcdn.com/h40/cn.png"),
    KRW("Won Sul-Coreano", "https://flagcdn.com/h40/kr.png"),
    JPY("Iene Japonês", "https://flagcdn.com/h40/jp.png"),
    PLN("Zlóti Polonês", "https://flagcdn.com/h40/pl.png"),
    GBP("Libra Esterlina", "https://flagcdn.com/h40/gb.png"),
    BYN("Rublo Bielorrusso", "https://flagcdn.com/h40/by.png"),
    HUF("Florim Húngaro", "https://flagcdn.com/h40/hu.png"),
    PHP("Peso Filipino", "https://flagcdn.com/h40/ph.png"),
    KWD("Dinar Kuwaitiano", "https://flagcdn.com/h40/kw.png"),
    RUB("Rublo Russo", "https://flagcdn.com/h40/ru.png"),
    PYG("Guarani Paraguaio", "https://flagcdn.com/h40/py.png"),
    COP("Peso Colombiano", "https://flagcdn.com/h40/co.png"),
    USD("Dólar Americano", "https://flagcdn.com/h40/us.png"),
    EGP("Libra Egípcia", "https://flagcdn.com/h40/eg.png"),
    SGD("Dólar de Cingapura", "https://flagcdn.com/h40/sg.png"),
    NIO("Córdoba Nicaraguense", "https://flagcdn.com/h40/ni.png"),
    PEN("Sol do Peru", "https://flagcdn.com/h40/pe.png"),
    ETH("Ethereum", "https://i.postimg.cc/yYFhQKRL/ethereum.png"),
    NZD("Dólar Neozelandês", "https://flagcdn.com/h40/nz.png"),
    BRL("Real Brasileiro", "https://flagcdn.com/h40/br.png"),;

    companion object {
        fun getByName(name: String):Coin {
            return values().find { it.name == name } ?: Coin.BRL
        }
    }
}