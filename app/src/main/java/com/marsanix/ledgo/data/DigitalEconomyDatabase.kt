package com.marsanix.ledgo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Database Room
@Database(entities = [TopicEntity::class, UserProgressEntity::class, AppSettingsEntity::class], version = 1)
abstract class DigitalEconomyDatabase : RoomDatabase() {
    // Abstract method untuk DAO
    abstract fun topicDao(): TopicDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun settingsDao(): AppSettingsDao

    // Companion object untuk singleton dan inisialisasi
    companion object {
        @Volatile
        private var INSTANCE: DigitalEconomyDatabase? = null

        // Migration sederhana
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Contoh migrasi: tambah kolom baru atau ubah struktur tabel
                db.execSQL("ALTER TABLE digital_economy_topics ADD COLUMN last_updated INTEGER")
            }
        }

        // Fungsi untuk mendapatkan instance database
        fun getInstance(context: Context): DigitalEconomyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        // Konstruksi database dengan callback
        private fun buildDatabase(context: Context): DigitalEconomyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                DigitalEconomyDatabase::class.java,
                "digital_economy_database"
            )
                .addMigrations(MIGRATION_1_2)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Inisialisasi data awal setelah database dibuat
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                initializeDefaultData(database)
                            }
                        }
                    }
                })
                .fallbackToDestructiveMigration() // Opsional: reset database jika migrasi gagal
                .allowMainThreadQueries()
                .build()
        }

        // Fungsi untuk inisialisasi data default
        private suspend fun initializeDefaultData(database: DigitalEconomyDatabase) {
            val topicDao = database.topicDao()
            val userProgressDao = database.userProgressDao()
            val settingsDao = database.settingsDao()

            // Cek apakah sudah ada data
            if ((topicDao.getAllTopics().value?.size ?: 0) == 0) {
                // Tambahkan topik default
                val defaultTopics = listOf(
                    TopicEntity(
                        id = -1,
                        title = "Pengantar",
                        slug = "pengantar",
                        description = "Pendahuluan",
                        difficulty = 1,
                        moduleCount = 5,
                        youtube = "",
                        isCompleted = false,
                        content = """
                            ## Pendahuluan
                
                            ### Definisi Ekonomi Digital
                            Ekonomi digital adalah sistem ekonomi yang memanfaatkan teknologi informasi dan komunikasi (TIK) untuk mentransformasi aktivitas ekonomi, menciptakan nilai tambah melalui proses digital, platform online, dan inovasi teknologi.
                            
                            ### Konteks Global
                            Ekonomi digital bukan sekadar tren, melainkan revolusi fundamental dalam cara kita berinteraksi, bertransaksi, dan menciptakan nilai ekonomi di era modern.
                
                             🚀
                        """.trimIndent()
                    ),
                    TopicEntity(
                        id = 1,
                        title = "Bab 1: Landasan Konseptual Ekonomi Digital",
                        slug = "landasan-konseptual-ekonomi-digital",
                        description = "Pengantar fundamental ekonomi digital",
                        difficulty = 1,
                        moduleCount = 5,
                        youtube = "29NSQLeh4qo",
                        isCompleted = false,
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
                    TopicEntity(
                        id = 2,
                        title = "Bab 2: Transformasi Ekonomi Digital",
                        slug = "transformasi-ekonomi-digital",
                        description = "Strategi dan model bisnis digital",
                        difficulty = 2,
                        moduleCount = 6,
                        youtube = "",
                        isCompleted = false,
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
                    TopicEntity(
                        id = 3,
                        title = "Bab 3: Infrastruktur dan Ekosistem",
                        slug = "infrastruktur-dan-ekosistem",
                        description = "AI, Blockchain, dan Inovasi Teknologi",
                        difficulty = 3,
                        moduleCount = 7,
                        youtube = "",
                        isCompleted = false,
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
                    TopicEntity(
                        id = 4,
                        title = "Bab 4: Tantangan dan Risiko",
                        slug = "tantangan-dan-risiko",
                        description = "Keamanan Data, Kesiapan Infrastruktur, Etika Teknologi Digital",
                        difficulty = 3,
                        moduleCount = 7,
                        youtube = "",
                        isCompleted = false,
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
                    TopicEntity(
                        id = 5,
                        title = "Bab 5: Strategi Pengembangan",
                        slug = "strategi-pengembangan",
                        description = "Strategi Individu, Strategi Organisasi,  Strategi Nasional",
                        difficulty = 3,
                        moduleCount = 7,
                        youtube = "",
                        isCompleted = false,
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
                    TopicEntity(
                        id = 6,
                        title = "Bab 6: Studi Kasus",
                        slug = "studi-kasus",
                        description = "Kasus Keberhasilan Global, Inovasi Lokal",
                        difficulty = 3,
                        moduleCount = 7,
                        youtube = "",
                        isCompleted = false,
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

                    TopicEntity(
                        id = 7,
                        title = "Kesimpulan",
                        description = "Kasus Keberhasilan Global, Inovasi Lokal",
                        slug = "kesimpulan",
                        difficulty = 3,
                        moduleCount = 7,
                        youtube = "",
                        isCompleted = false,
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
                )



                )

                // Simpan topik default
                topicDao.insertTopics(defaultTopics)

                // Inisialisasi progress default untuk setiap topik
                val defaultProgress = defaultTopics.map { topic ->
                    UserProgressEntity(
                        topicId = topic.id,
                        completedModules = 0,
                        totalScore = 0
                    )
                }

                // Simpan progress default
                defaultProgress.forEach { progress ->
                    userProgressDao.updateProgress(progress)
                }
            }
        }

        // Fungsi utilitas untuk menghapus database (opsional)
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}