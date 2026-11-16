package com.example.belajarshalat

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

data class Prayer(val name: String, val rakats: Int, val summary: String)

data class Lesson(
    val title: String,
    val arabic: String,
    val latin: String,
    val meaning: String,
    val movement: String,
    val voiceText: String,
    val note: String = ""
)

private val prayers = listOf(
    Prayer("Subuh", 2, "2 rakaat"),
    Prayer("Dzuhur", 4, "4 rakaat"),
    Prayer("Ashar", 4, "4 rakaat"),
    Prayer("Maghrib", 3, "3 rakaat"),
    Prayer("Isya", 4, "4 rakaat")
)

private val lessons = listOf(
    Lesson("1. Niat", "نَوَيْتُ أَنْ أُصَلِّيَ فَرْضَ الصُّبْحِ رَكْعَتَيْنِ لِلّٰهِ تَعَالَى",
        "Nawaitu an usholliya fardhash-shubhi rak'ataini lillaahi ta'aalaa.",
        "Aku berniat melaksanakan sholat fardu Subuh dua rakaat karena Allah Ta'ala.",
        "BERDIRI", "Nawaitu an usholliya fardhash-shubhi rak'ataini lillaahi ta'aalaa.",
        "Niat tempatnya di dalam hati. Ubah nama sholat dan jumlah rakaat sesuai sholat yang dikerjakan."),
    Lesson("2. Takbiratul ihram", "اللّٰهُ أَكْبَرُ", "Allaahu akbar.",
        "Allah Maha Besar.", "TAKBIR", "Allaahu akbar."),
    Lesson("3. Doa iftitah", "اللّٰهُ أَكْبَرُ كَبِيرًا وَالْحَمْدُ لِلّٰهِ كَثِيرًا وَسُبْحَانَ اللّٰهِ بُكْرَةً وَأَصِيلًا",
        "Allaahu akbaru kabiiraa, walhamdu lillaahi katsiiraa, wa subhaanallaahi bukratan wa ashiilaa.",
        "Allah Maha Besar dengan sebesar-besarnya. Segala puji bagi Allah sebanyak-banyaknya. Mahasuci Allah pada waktu pagi dan petang.",
        "BERDIRI", "Allaahu akbaru kabiiraa, walhamdu lillaahi katsiiraa, wa subhaanallaahi bukratan wa ashiilaa.",
        "Sunah; dibaca pada rakaat pertama setelah takbiratul ihram."),
    Lesson("4. Al-Fatihah", "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيْمِ\nالْحَمْدُ لِلّٰهِ رَبِّ الْعَالَمِيْنَ\nالرَّحْمٰنِ الرَّحِيْمِ\nمَالِكِ يَوْمِ الدِّيْنِ\nإِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِيْنُ\nاهْدِنَا الصِّرَاطَ الْمُسْتَقِيْمَ\nصِرَاطَ الَّذِيْنَ أَنْعَمْتَ عَلَيْهِمْ\nغَيْرِ الْمَغْضُوْبِ عَلَيْهِمْ وَلَا الضَّالِّيْنَ",
        "Bismillaahir rahmaanir rahiim. Alhamdu lillaahi rabbil 'aalamiin. Ar-rahmaanir rahiim. Maaliki yaumid diin. Iyyaaka na'budu wa iyyaaka nasta'iin. Ihdinash-shiraathal mustaqiim. Shiraathalladziina an'amta 'alaihim, ghairil maghdhuubi 'alaihim waladh-dhaalliin.",
        "Dengan nama Allah Yang Maha Pengasih, Maha Penyayang. Segala puji bagi Allah, Tuhan seluruh alam. Yang Maha Pengasih, Maha Penyayang. Pemilik hari pembalasan. Hanya kepada-Mu kami menyembah dan hanya kepada-Mu kami memohon pertolongan. Tunjukilah kami jalan yang lurus, yaitu jalan orang-orang yang Engkau beri nikmat, bukan jalan mereka yang dimurkai dan bukan pula jalan mereka yang sesat.",
        "BERDIRI", "Bismillaahir rahmaanir rahiim. Alhamdu lillaahi rabbil 'aalamiin. Ar-rahmaanir rahiim. Maaliki yaumid diin. Iyyaaka na'budu wa iyyaaka nasta'iin. Ihdinash-shiraathal mustaqiim. Shiraathalladziina an'amta 'alaihim, ghairil maghdhuubi 'alaihim waladh-dhaalliin.",
        "Baca Al-Fatihah pada setiap rakaat. Pastikan pelafalan dipelajari dari guru/ustaz yang fasih."),
    Lesson("5. Surat pendek (Al-Ikhlas)", "قُلْ هُوَ اللّٰهُ أَحَدٌ\nاللّٰهُ الصَّمَدُ\nلَمْ يَلِدْ وَلَمْ يُولَدْ\nوَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ",
        "Qul huwallaahu ahad. Allaahush-shamad. Lam yalid wa lam yuulad. Wa lam yakul lahu kufuwan ahad.",
        "Katakanlah: Dialah Allah Yang Maha Esa. Allah tempat meminta segala sesuatu. Dia tidak beranak dan tidak diperanakkan. Tidak ada sesuatu pun yang setara dengan-Nya.",
        "BERDIRI", "Qul huwallaahu ahad. Allaahush-shamad. Lam yalid wa lam yuulad. Wa lam yakul lahu kufuwan ahad.",
        "Contoh surat pendek; dibaca setelah Al-Fatihah pada rakaat pertama dan kedua."),
    Lesson("6. Rukuk", "سُبْحَانَ رَبِّيَ الْعَظِيْمِ وَبِحَمْدِهِ",
        "Subhaana rabbiyal 'azhiimi wa bihamdih.", "Mahasuci Tuhanku Yang Maha Agung dan segala puji bagi-Nya.",
        "RUKUK", "Subhaana rabbiyal 'azhiimi wa bihamdih. Subhaana rabbiyal 'azhiimi wa bihamdih. Subhaana rabbiyal 'azhiimi wa bihamdih."),
    Lesson("7. Iktidal", "سَمِعَ اللّٰهُ لِمَنْ حَمِدَهُ\nرَبَّنَا وَلَكَ الْحَمْدُ",
        "Sami'allaahu liman hamidah. Rabbanaa wa lakal hamdu.",
        "Allah mendengar orang yang memuji-Nya. Wahai Tuhan kami, bagi-Mu segala puji.",
        "IKTIDAL", "Sami'allaahu liman hamidah. Rabbanaa wa lakal hamdu."),
    Lesson("8. Sujud", "سُبْحَانَ رَبِّيَ الْأَعْلَى وَبِحَمْدِهِ",
        "Subhaana rabbiyal a'laa wa bihamdih.", "Mahasuci Tuhanku Yang Maha Tinggi dan segala puji bagi-Nya.",
        "SUJUD", "Subhaana rabbiyal a'laa wa bihamdih. Subhaana rabbiyal a'laa wa bihamdih. Subhaana rabbiyal a'laa wa bihamdih."),
    Lesson("9. Duduk antara dua sujud", "رَبِّ اغْفِرْ لِي وَارْحَمْنِي وَاجْبُرْنِي وَارْفَعْنِي وَارْزُقْنِي وَاهْدِنِي وَعَافِنِي وَاعْفُ عَنِّي",
        "Rabbighfirlii, warhamnii, wajburnii, warfa'nii, warzuqnii, wahdinii, wa'aafinii, wa'fu 'annii.",
        "Ya Allah, ampunilah aku, rahmatilah aku, cukupilah kekuranganku, angkatlah derajatku, berilah rezeki dan petunjuk kepadaku, sehatkanlah aku, dan maafkanlah aku.",
        "DUDUK", "Rabbighfirlii, warhamnii, wajburnii, warfa'nii, warzuqnii, wahdinii, wa'aafinii, wa'fu 'annii."),
    Lesson("10. Tahiyat awal", "التَّحِيَّاتُ الْمُبَارَكَاتُ الصَّلَوَاتُ الطَّيِّبَاتُ لِلّٰهِ\nالسَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللّٰهِ وَبَرَكَاتُهُ\nالسَّلَامُ عَلَيْنَا وَعَلَى عِبَادِ اللّٰهِ الصَّالِحِيْنَ\nأَشْهَدُ أَنْ لَا إِلٰهَ إِلَّا اللّٰهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا رَسُوْلُ اللّٰهِ",
        "Attahiyyaatul mubaarakaatush-shalawaatuth-thayyibaatu lillaah. Assalaamu 'alaika ayyuhan-nabiyyu wa rahmatullaahi wa barakaatuh. Assalaamu 'alainaa wa 'alaa 'ibaadillaahish-shaalihiin. Asyhadu allaa ilaaha illallaah wa asyhadu anna Muhammadar rasuulullaah.",
        "Segala penghormatan, keberkahan, doa, dan kebaikan adalah milik Allah. Semoga keselamatan, rahmat Allah, dan keberkahan-Nya tercurah kepadamu wahai Nabi. Semoga keselamatan tercurah kepada kami dan hamba Allah yang saleh. Aku bersaksi tidak ada Tuhan selain Allah dan Muhammad adalah utusan Allah.",
        "DUDUK", "Attahiyyaatul mubaarakaatush-shalawaatuth-thayyibaatu lillaah. Assalaamu 'alaika ayyuhan-nabiyyu wa rahmatullaahi wa barakaatuh. Assalaamu 'alainaa wa 'alaa 'ibaadillaahish-shaalihiin. Asyhadu allaa ilaaha illallaah wa asyhadu anna Muhammadar rasuulullaah.",
        "Dibaca setelah rakaat kedua pada sholat 3 atau 4 rakaat."),
    Lesson("11. Tahiyat akhir dan shalawat", "اللّٰهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ\nكَمَا صَلَّيْتَ عَلَى إِبْرَاهِيْمَ وَعَلَى آلِ إِبْرَاهِيْمَ\nوَبَارِكْ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ\nكَمَا بَارَكْتَ عَلَى إِبْرَاهِيْمَ وَعَلَى آلِ إِبْرَاهِيْمَ\nإِنَّكَ حَمِيْدٌ مَجِيْدٌ",
        "Allaahumma shalli 'alaa Muhammadin wa 'alaa aali Muhammad. Kamaa shallaita 'alaa Ibraahiima wa 'alaa aali Ibraahiim. Wa baarik 'alaa Muhammadin wa 'alaa aali Muhammad. Kamaa baarakta 'alaa Ibraahiima wa 'alaa aali Ibraahiim. Innaka hamiidum majiid.",
        "Ya Allah, limpahkan rahmat dan keberkahan kepada Nabi Muhammad dan keluarganya sebagaimana Engkau melimpahkan rahmat dan keberkahan kepada Nabi Ibrahim dan keluarganya. Sesungguhnya Engkau Maha Terpuji lagi Maha Mulia.",
        "DUDUK", "Allaahumma shalli 'alaa Muhammadin wa 'alaa aali Muhammad. Kamaa shallaita 'alaa Ibraahiima wa 'alaa aali Ibraahiim. Wa baarik 'alaa Muhammadin wa 'alaa aali Muhammad. Kamaa baarakta 'alaa Ibraahiima wa 'alaa aali Ibraahiim. Innaka hamiidum majiid.",
        "Dibaca setelah tahiyat pada duduk terakhir."),
    Lesson("12. Salam", "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللّٰهِ",
        "Assalaamu'alaikum wa rahmatullaah.", "Semoga keselamatan dan rahmat Allah tercurah kepada kalian.",
        "SALAM", "Assalaamu'alaikum wa rahmatullaah. Assalaamu'alaikum wa rahmatullaah.",
        "Menoleh ke kanan lalu ke kiri.")
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    val explanation: String
)

private val quizQuestions = listOf(
    QuizQuestion("Gerakan setelah rukuk adalah ...",
        listOf("Sujud", "Iktidal", "Duduk tahiyat"), 1,
        "Setelah rukuk, bangkit berdiri dengan iktidal."),
    QuizQuestion("Bacaan yang dibaca pada setiap rakaat adalah ...",
        listOf("Al-Fatihah", "Al-Ikhlas saja", "Doa iftitah"), 0,
        "Al-Fatihah dibaca pada setiap rakaat."),
    QuizQuestion("Shalat Maghrib terdiri dari ... rakaat.",
        listOf("2", "3", "4"), 1,
        "Shalat Maghrib terdiri dari tiga rakaat."),
    QuizQuestion("Gerakan terakhir sebelum salam adalah ...",
        listOf("Rukuk", "Tahiyat akhir", "Iktidal"), 1,
        "Setelah tahiyat akhir, shalat diakhiri dengan salam.")
)

class MainActivity : ComponentActivity() {
    private var tts: TextToSpeech? = null
    private var ttsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this) { status ->
            ttsReady = status == TextToSpeech.SUCCESS
        }
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(
                primary = Color(0xFF176B55),
                secondary = Color(0xFFB8860B),
                background = Color(0xFFF7F8F5)
            )) {
                ShalatApp(
                    onSpeak = { text ->
                        if (ttsReady) {
                            tts?.language = Locale("id", "ID")
                            tts?.setSpeechRate(0.82f)
                            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "lesson_voice")
                        }
                    },
                    onStopSpeak = { tts?.stop() }
                )
            }
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}

@Composable
fun ShalatApp(onSpeak: (String) -> Unit, onStopSpeak: () -> Unit) {
    var selectedPrayer by remember { mutableStateOf(prayers[0]) }
    var lessonIndex by remember { mutableIntStateOf(0) }
    var showGuide by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val completedLessons = remember { mutableStateListOf<Int>() }
    var showQuiz by remember { mutableStateOf(false) }
    var quizIndex by remember { mutableIntStateOf(0) }
    var quizSelection by remember { mutableIntStateOf(-1) }
    var quizScore by remember { mutableIntStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }
    val lesson = lessons[lessonIndex]
    val filteredLessons = lessons.withIndex().filter {
        it.value.title.contains(searchText, ignoreCase = true) ||
            it.value.latin.contains(searchText, ignoreCase = true) ||
            it.value.meaning.contains(searchText, ignoreCase = true) ||
            it.value.movement.contains(searchText, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBarSimple(title = "Belajar Shalat")
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Belajar bertahap, satu gerakan dan bacaan setiap kali.", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
            Text("Pilih sholat", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                prayers.forEach { prayer ->
                    FilterChip(
                        selected = selectedPrayer == prayer,
                        onClick = { selectedPrayer = prayer },
                        label = { Text(prayer.name) }
                    )
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F2ED))) {
                Column(Modifier.padding(14.dp)) {
                    Text("Sholat ${selectedPrayer.name}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("${selectedPrayer.rakats} rakaat • ${selectedPrayer.summary}")
                    Text(
                        when (selectedPrayer.name) {
                            "Subuh" -> "Dua rakaat, diakhiri tahiyat akhir dan salam. Qunut dapat dipelajari sebagai amalan sunah menurut mazhab Syafi'i."
                            "Maghrib" -> "Dua rakaat pertama, tahiyat awal, satu rakaat terakhir, lalu tahiyat akhir dan salam."
                            else -> "Dua rakaat pertama, tahiyat awal, dua rakaat berikutnya, lalu tahiyat akhir dan salam."
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Cari bacaan atau gerakan") },
                singleLine = true
            )
            if (searchText.isNotBlank()) {
                Card {
                    Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Hasil pencarian", fontWeight = FontWeight.Bold)
                        if (filteredLessons.isEmpty()) {
                            Text("Materi tidak ditemukan.")
                        } else {
                            filteredLessons.forEach { item ->
                                TextButton(
                                    onClick = {
                                        lessonIndex = item.index
                                        searchText = ""
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) { Text(item.value.title, modifier = Modifier.fillMaxWidth()) }
                            }
                        }
                    }
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F2ED))) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Progres belajar", fontWeight = FontWeight.Bold)
                    Text("${completedLessons.size} dari ${lessons.size} materi selesai")
                    LinearProgressIndicator(
                        progress = { completedLessons.size.toFloat() / lessons.size },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("Tandai materi setelah selesai dipelajari agar perkembanganmu terlihat.")
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Materi bacaan", fontWeight = FontWeight.Bold)
                Text("${lessonIndex + 1} / ${lessons.size}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            LinearProgressIndicator(progress = { (lessonIndex + 1).toFloat() / lessons.size }, modifier = Modifier.fillMaxWidth())

            Card {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(lesson.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    MovementIllustration(lesson.movement)
                    Text(lesson.movement, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Text("Arab", fontWeight = FontWeight.Bold)
                    Text(lesson.arabic, fontSize = 25.sp, lineHeight = 42.sp, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                    Text("Latin", fontWeight = FontWeight.Bold)
                    Text(lesson.latin, fontSize = 16.sp, lineHeight = 25.sp)
                    Text("Arti", fontWeight = FontWeight.Bold)
                    Text(lesson.meaning, lineHeight = 23.sp)
                    if (lesson.note.isNotBlank()) {
                        Text(lesson.note, color = Color(0xFF795A16), style = MaterialTheme.typography.bodySmall)
                    }
                    Button(onClick = { onSpeak(lesson.voiceText) }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.VolumeUp, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Putar / ulangi bacaan")
                    }
                    OutlinedButton(onClick = onStopSpeak, modifier = Modifier.fillMaxWidth()) {
                        Text("Hentikan suara")
                    }
                    Text("Catatan: suara dibuat oleh Text-to-Speech di perangkat. Pelafalan Arab mungkin tidak sempurna; gunakan guru atau rekaman qari untuk memeriksa tajwid.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    OutlinedButton(
                        onClick = {
                            if (completedLessons.contains(lessonIndex)) completedLessons.remove(lessonIndex)
                            else completedLessons.add(lessonIndex)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (completedLessons.contains(lessonIndex)) "Batalkan tanda selesai" else "Tandai materi selesai")
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { lessonIndex = (lessonIndex - 1).coerceAtLeast(0) },
                    enabled = lessonIndex > 0,
                    modifier = Modifier.weight(1f)
                ) { Icon(Icons.Default.ArrowBack, contentDescription = null); Text("Sebelumnya") }
                Button(
                    onClick = { lessonIndex = (lessonIndex + 1).coerceAtMost(lessons.lastIndex) },
                    enabled = lessonIndex < lessons.lastIndex,
                    modifier = Modifier.weight(1f)
                ) { Text("Berikutnya"); Icon(Icons.Default.ArrowForward, contentDescription = null) }
            }
            OutlinedButton(onClick = { showGuide = !showGuide }, modifier = Modifier.fillMaxWidth()) {
                Text(if (showGuide) "Tutup urutan sholat" else "Lihat urutan rakaat")
            }
            if (showGuide) {
                Card {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Urutan sholat ${selectedPrayer.name}", fontWeight = FontWeight.Bold)
                        Text("Rakaat 1: berdiri, Al-Fatihah, surat pendek, rukuk, iktidal, sujud, duduk, sujud.")
                        Text("Rakaat 2: Al-Fatihah, surat pendek, rukuk, iktidal, sujud, duduk, sujud.")
                        if (selectedPrayer.rakats > 2) Text("Setelah rakaat 2: duduk tahiyat awal, lalu berdiri.")
                        if (selectedPrayer.rakats == 3) Text("Rakaat 3: Al-Fatihah, rukuk, iktidal, sujud, duduk tahiyat akhir, salam.")
                        if (selectedPrayer.rakats == 4) Text("Rakaat 3 dan 4: Al-Fatihah pada tiap rakaat, rukuk, iktidal, dua sujud. Setelah rakaat 4, tahiyat akhir dan salam.")
                        if (selectedPrayer.rakats == 2) Text("Setelah rakaat 2: tahiyat akhir dan salam.")
                    }
                }
            }
            Button(
                onClick = {
                    quizIndex = 0
                    quizSelection = -1
                    quizScore = 0
                    quizFinished = false
                    showQuiz = true
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Mulai kuis latihan") }
            Text("Materi ini adalah panduan belajar. Perbedaan bacaan sunah dapat ditemukan antarmazhab; verifikasi bacaan dan gerakan kepada guru agama yang tepercaya.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Spacer(Modifier.height(20.dp))
        }
    }

    if (showQuiz) {
        AlertDialog(
            onDismissRequest = { showQuiz = false },
            title = { Text(if (quizFinished) "Hasil kuis" else "Kuis shalat ${quizIndex + 1}/${quizQuestions.size}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (quizFinished) {
                        Text("Skor kamu: $quizScore dari ${quizQuestions.size}")
                        Text(if (quizScore == quizQuestions.size) "Bagus! Semua jawaban benar." else "Terus belajar dan coba lagi. Tidak apa-apa jika belum semuanya benar.")
                    } else {
                        val q = quizQuestions[quizIndex]
                        Text(q.question, fontWeight = FontWeight.Bold)
                        q.options.forEachIndexed { index, option ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = quizSelection == index,
                                    onClick = { quizSelection = index }
                                )
                                Text(option)
                            }
                        }
                        if (quizSelection >= 0) {
                            Text(
                                if (quizSelection == q.answerIndex) "Benar! ${q.explanation}" else "Belum tepat. ${q.explanation}",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            confirmButton = {
                if (quizFinished) {
                    TextButton(onClick = { showQuiz = false }) { Text("Selesai") }
                } else {
                    TextButton(
                        enabled = quizSelection >= 0,
                        onClick = {
                            if (quizSelection == quizQuestions[quizIndex].answerIndex) quizScore++
                            if (quizIndex == quizQuestions.lastIndex) {
                                quizFinished = true
                            } else {
                                quizIndex++
                                quizSelection = -1
                            }
                        }
                    ) { Text(if (quizIndex == quizQuestions.lastIndex) "Lihat skor" else "Lanjut") }
                }
            },
            dismissButton = {
                if (!quizFinished) TextButton(onClick = { showQuiz = false }) { Text("Tutup") }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSimple(title: String) {
    CenterAlignedTopAppBar(title = { Text(title, fontWeight = FontWeight.Bold) })
}

@Composable
fun MovementIllustration(type: String) {
    // Ilustrasi skematis buatan aplikasi, bukan foto dan bukan panduan anatomi rinci.
    Canvas(
        modifier = Modifier.fillMaxWidth().height(170.dp)
            .background(Color(0xFFF1F5F2), RoundedCornerShape(16.dp))
    ) {
        val cx = size.width / 2
        val baseY = size.height * 0.82f
        val ink = Color(0xFF176B55)
        val skin = Color(0xFFB8860B)
        val head = 17f
        val torsoTop = size.height * 0.30f
        val torsoBottom = size.height * 0.58f
        val line = 9f

        fun limb(a: Offset, b: Offset, width: Float = line) {
            drawLine(ink, a, b, width, cap = StrokeCap.Round)
        }
        when (type) {
            "RUKUK" -> {
                drawCircle(skin, head, Offset(cx + 52f, size.height * .42f))
                limb(Offset(cx - 5f, size.height * .40f), Offset(cx + 48f, size.height * .48f), 22f)
                limb(Offset(cx - 5f, size.height * .45f), Offset(cx - 55f, size.height * .65f))
                limb(Offset(cx - 55f, size.height * .65f), Offset(cx - 80f, baseY))
                limb(Offset(cx + 5f, size.height * .48f), Offset(cx + 58f, size.height * .65f))
                limb(Offset(cx + 58f, size.height * .65f), Offset(cx + 80f, baseY))
            }
            "SUJUD" -> {
                drawCircle(skin, head, Offset(cx + 60f, size.height * .68f))
                limb(Offset(cx - 35f, size.height * .65f), Offset(cx + 45f, size.height * .65f), 22f)
                limb(Offset(cx - 20f, size.height * .65f), Offset(cx - 70f, size.height * .86f))
                limb(Offset(cx + 20f, size.height * .65f), Offset(cx + 70f, size.height * .86f))
                limb(Offset(cx - 30f, size.height * .65f), Offset(cx - 65f, size.height * .90f))
            }
            "DUDUK" -> {
                drawCircle(skin, head, Offset(cx, size.height * .28f))
                limb(Offset(cx, size.height * .36f), Offset(cx, size.height * .62f), 22f)
                limb(Offset(cx, size.height * .62f), Offset(cx - 65f, size.height * .75f))
                limb(Offset(cx - 65f, size.height * .75f), Offset(cx + 60f, size.height * .75f))
                limb(Offset(cx, size.height * .60f), Offset(cx + 65f, size.height * .75f))
            }
            "TAKBIR" -> {
                drawCircle(skin, head, Offset(cx, size.height * .18f))
                limb(Offset(cx, size.height * .28f), Offset(cx, size.height * .62f), 22f)
                limb(Offset(cx - 5f, size.height * .38f), Offset(cx - 45f, size.height * .12f))
                limb(Offset(cx + 5f, size.height * .38f), Offset(cx + 45f, size.height * .12f))
                limb(Offset(cx, size.height * .62f), Offset(cx - 30f, baseY))
                limb(Offset(cx, size.height * .62f), Offset(cx + 30f, baseY))
            }
            else -> {
                drawCircle(skin, head, Offset(cx, size.height * .18f))
                limb(Offset(cx, torsoTop), Offset(cx, torsoBottom), 24f)
                limb(Offset(cx - 5f, size.height * .36f), Offset(cx - 38f, size.height * .52f))
                limb(Offset(cx + 5f, size.height * .36f), Offset(cx + 38f, size.height * .52f))
                limb(Offset(cx, torsoBottom), Offset(cx - 28f, baseY))
                limb(Offset(cx, torsoBottom), Offset(cx + 28f, baseY))
            }
        }
        drawLine(Color.LightGray, Offset(size.width * .18f, baseY + 3), Offset(size.width * .82f, baseY + 3), 3f)
    }
}
