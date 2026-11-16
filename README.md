# Belajar Shalat — Android starter project

## Isi fitur
- Pilih sholat Subuh, Dzuhur, Ashar, Maghrib, atau Isya.
- Materi bertahap dengan teks Arab, latin, dan arti.
- Tombol suara menggunakan Android Text-to-Speech (TTS) bawaan perangkat.
- Ilustrasi skematis gerakan yang digambar langsung oleh Jetpack Compose.
- Ringkasan urutan rakaat untuk setiap sholat.

## Cara membuka
1. Ekstrak ZIP.
2. Buka Android Studio.
3. Pilih **Open** lalu pilih folder `BelajarShalat`.
4. Tunggu Gradle Sync selesai.
5. Sambungkan ponsel Android dengan USB debugging aktif, atau gunakan emulator.
6. Tekan **Run**.

## Membuat APK
Di Android Studio pilih **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
APK debug biasanya tersedia di:
`app/build/outputs/apk/debug/app-debug.apk`

## Catatan suara
Tombol suara memakai TextToSpeech perangkat. Hasil suara tergantung mesin TTS dan bahasa yang tersedia. TTS bukan pengganti contoh pelafalan Al-Qur'an/tajwid dari guru atau qari. Untuk versi rilis, sebaiknya tambahkan rekaman audio Arab yang sudah diverifikasi.

## Catatan materi
Materi ini starter untuk belajar. Teks transliterasi dan ringkasan fikih perlu ditinjau guru agama/ustaz sebelum dipublikasikan. Niat berada di hati; contoh lafal di aplikasi hanya bantuan belajar. Qunut Subuh merupakan amalan sunah dalam mazhab Syafi'i, bukan kewajiban universal.
\nFitur tambahan pada versi ini: pencarian materi, penanda progres belajar, dan kuis latihan singkat. Progres saat ini bersifat sementara selama aplikasi berjalan; penyimpanan permanen dapat ditambahkan pada tahap berikutnya.\n

## Membuat APK melalui HP (tanpa laptop)

1. Buat akun GitHub dan masuk melalui browser.
2. Buat repository baru, misalnya `belajar-shalat`.
3. Unggah seluruh isi ZIP proyek ini ke repository (termasuk folder tersembunyi `.github`).
4. Buka tab **Actions**, pilih workflow **Build Belajar Shalat APK**, lalu tekan **Run workflow**.
5. Tunggu sampai proses selesai dengan tanda berhasil.
6. Buka hasil run tersebut, unduh artifact **BelajarShalat-debug-apk**, lalu ekstrak ZIP artifact.
7. Pasang `app-debug.apk` di Vivo Y20. Android mungkin meminta izin untuk memasang aplikasi dari browser atau Files; aktifkan hanya untuk sumber yang kamu percaya.

Catatan: workflow ini menyiapkan build otomatis, tetapi belum menjamin build akan sukses sebelum GitHub menjalankannya. Jika build gagal, buka log Actions dan perbaiki error yang dilaporkan. APK debug cocok untuk uji coba pribadi, bukan distribusi resmi ke Play Store.
