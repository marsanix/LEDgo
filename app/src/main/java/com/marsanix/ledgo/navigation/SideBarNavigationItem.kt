package com.marsanix.pelajarankuapp

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.marsanix.ledgo.R


enum class SideBarNavigationItem(
    val id: Int,
    val title: String,
    val description: String,
    val icon: Int,
    val slug: String = "",
    val content: String = "",
    val youtube: String = ""
) {

    // Pengantar jangan di hapus, ubah hanya pada bagian content
    @SuppressLint("ResourceType")
    Pengantar(
        id = -1,
        icon = R.drawable.baseline_menu_book_24,
        title = "Pengantar",
        description = "Pendahuluan",
        slug = "pengantar",
        content = """
                    ## Pendahuluan
        
                    ### Definisi Ekonomi Digital
                    Ekonomi digital adalah sistem ekonomi yang memanfaatkan teknologi informasi dan komunikasi (TIK) untuk mentransformasi aktivitas ekonomi, menciptakan nilai tambah melalui proses digital, platform online, dan inovasi teknologi.
                    
                    ### Konteks Global
                    Ekonomi digital bukan sekadar tren, melainkan revolusi fundamental dalam cara kita berinteraksi, bertransaksi, dan menciptakan nilai ekonomi di era modern.
        
                     🚀
                """.trimIndent(),

    ),
    Materi1(
        id = 1,
        icon = R.drawable.baseline_menu_book_24,
        title = "Bab 1: Landasan Konseptual Ekonomi Digital",
        description = "Pengantar fundamental ekonomi digital",
        slug = "landasan-konseptual-ekonomi-digital",
        youtube = "29NSQLeh4qo",
        content = """
            ### 1.1 Karakteristik Utama
            1. **Keterhubungan Global**: Teknologi menghapuskan batas geografis
            2. **Aksesibilitas**: Informasi dan layanan tersedia secara real-time
            3. **Inovasi Berkelanjutan**: Perubahan cepat melalui teknologi baru
            
            ### 1.2 Komponen Inti
            - Infrastruktur Digital
            - Platform Teknologi
            - Sumber Daya Manusia Terampil
            - Kebijakan Pendukung
             🚀😊
        """.trimIndent()
    ),
    Materi2(
        id = 2,
        icon = R.drawable.baseline_menu_book_24,
        title = "Bab 2: Transformasi Ekonomi Digital",
        description = "Strategi dan model bisnis digital",
        slug = "transformasi-ekonomi-digital",
        youtube = "6VibHppH68A",
        content = """
            ### 2.1 Model Bisnis Baru
            1. **E-Commerce**
               - Platform jual-beli daring
               - Marketplace global
               - Personalisasi pengalaman konsumen

            2. **Platform Berbagi (Sharing Economy)**
               - Transportasi daring
               - Akomodasi alternatif
               - Jasa berbasis platform

            3. **Ekonomi Berbasis Langganan**
               - Layanan berlangganan digital
               - Akses dibanding kepemilikan
               - Contoh: Netflix, Spotify

            ### 2.2 Teknologi Pendorong
            - Kecerdasan Buatan (AI)
            - Blockchain
            - Internet of Things (IoT)
            - Big Data Analytics
            - Cloud Computing

        """.trimIndent()
    ),
    Materi3(
        id = 3,
        icon = R.drawable.baseline_menu_book_24,
        title = "Bab 3: Infrastruktur dan Ekosistem",
        description = "AI, Blockchain, dan Inovasi Teknologi",
        slug = "infrastruktur-dan-ekosistem",
        youtube = "fIOHo8Z0zAc",
        content = """
            ### 3.1 Komponen Infrastruktur
            1. Jaringan Telekomunikasi
            2. Pusat Data
            3. Keamanan Siber
            4. Platform Pembayaran Digital

            ### 3.2 Ekosistem Pendukung
            - Startup Teknologi
            - Investor Modal Ventura
            - Lembaga Pendidikan
            - Kebijakan Pemerintah

        """.trimIndent()
    ),
    Materi4(
        id = 4,
        icon = R.drawable.baseline_menu_book_24,
        title = "Bab 4: Tantangan dan Risiko",
        description = "Keamanan Data, Kesiapan Infrastruktur, Etika Teknologi Digital",
        slug = "tantangan-dan-risiko",
        youtube = "Ig1qzVrQetk",
        content = """
            ### 4.1 Tantangan Teknis
            - Keamanan Data
            - Privasi Pengguna
            - Ketimpangan Digital
            - Kesiapan Infrastruktur

            ### 4.2 Aspek Sosial
            - Pergeseran Lapangan Kerja
            - Kebutuhan Reskilling
            - Etika Teknologi Digital

        """.trimIndent()
    ),
    Materi5(
        id = 5,
        icon = R.drawable.baseline_menu_book_24,
        title = "Bab 5: Strategi Pengembangan",
        description = "Strategi Individu, Strategi Organisasi,  Strategi Nasional",
        slug = "strategi-pengembangan",
        youtube = "aZiER2bnNX4",
        content = """
            ### 5.1 Strategi Individu
            1. Pengembangan Keterampilan Digital
            2. Adaptasi Berkelanjutan
            3. Literasi Teknologi
            
            ### 5.2 Strategi Organisasi
            - Transformasi Digital
            - Inovasi Berkelanjutan
            - Kolaborasi Lintas Sektor
            
            ### 5.3 Strategi Nasional
            - Kebijakan Pendukung
            - Infrastruktur Digital
            - Pendidikan dan Pelatihan


        """.trimIndent()
    ),

    Materi6(
        id = 6,
        icon = R.drawable.baseline_menu_book_24,
        title = "Bab 6: Studi Kasus",
        description = "Kasus Keberhasilan Global, Inovasi Lokal",
        slug = "studi-kasus",
        youtube = "aZiER2bnNX4",
        content = """
           ### 6.1 Kasus Keberhasilan Global
            1. **Amazon**: Transformasi Ritel
            2. **Alibaba**: Ekosistem Digital Terintegrasi
            3. **Gojek Indonesia**: Platform Multiservices
            
            ### 6.2 Inovasi Lokal
            - Startup Teknologi Indonesia
            - Platform Pembayaran Digital
            - E-Commerce Nasional

        """.trimIndent()
    ),

    Kesimpulan(
        id = 7,
        icon = R.drawable.baseline_menu_book_24,
        title = "Kesimpulan",
        description = "Kasus Keberhasilan Global, Inovasi Lokal",
        slug = "kesimpulan",
        youtube = "aZiER2bnNX4",
        content = """

            Ekonomi digital bukanlah masa depan, melainkan realitas saat ini. Kemampuan adaptasi, inovasi, dan literasi digital menjadi kunci keberhasilan individu dan organisasi.
            
            ## Rekomendasi Lanjutan
            
            1. Terus-menerus belajar
            2. Eksplorasi teknologi baru
            3. Membangun jaringan digital
            4. Berpikir kritis terhadap perkembangan teknologi
            
            ## Daftar Pustaka
            
            1. Tapscott, D. (2014). *Ekonomi Digital*. Gramedia Pustaka Utama.
            2. Negroponte, N. (1995). *Being Digital*. Knopf.
            3. OECD Digital Economy Outlook (2021)
            4. Laporan Kementerian Komunikasi dan Informatika RI (2022)

        """.trimIndent()
    ),


    // Setting jangan dihapus dan jangan diubah
    Setting(
        id = 100,
        icon = R.drawable.baseline_settings_24,
        title = "Setting",
        description = "",
    )

}