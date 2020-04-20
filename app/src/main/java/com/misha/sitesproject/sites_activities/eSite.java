package com.misha.sitesproject.sites_activities;

import java.util.ArrayList;
import java.util.List;

public enum eSite {
    // ramat hagolan
    HAKFAR_BEKATSE_HAAR("הכפר בקצה ההר",1869817,"giv%60at-yo%27av", 32.7943774,35.6769714),
    YAIR_ADOM("יער אדום",214240,"qiryat-shemona", 33.2190665,35.7541805),
    MIZAD_ATRAT("מצד עטרת",213119,"gadot", 33.004838,35.627733),
    AIN_KSHATOT(    "עין קשתות",1869827,"natur", 32.8496085,35.7398508),
    AIN_SHOKO("עין שוקו",214243,"tiberias", 32.2799476,35.7059119),
    RISER_HAZAFON("רייזר הצפון",214376,"korazim", 32.9049935,35.5733247),
    SHMURAT_BRIHAT_BERAON("שמורת בריכת בראון",1869836,"merom-golan", 33.1438421,35.7755373),

    // galil elyon
    ROSH_HANIKRA("ראש הנקרה",214239,"nahariyya", 33.0861999,35.1200279),
    HAR_HASHFANIM("הר השפנים", 214299, "karmi%27el", 32.9644787,35.4136208),
    NAHAL_ZALMON("נחל צלמון",  214233,"akko",  31.8937973,34.9611941),
    MAARAT_PAAR("מערת פער", 214241, "pinna", 33.0313455,35.3879467),
    SHMURAT_NAHAL_DISHON("שמורת נחל דישון", 214361, "yir%27on",  32.4192234,35.7079882),
    SHMURAT_BREICHAT_DOVEV("שמורת בריכת דובב", 214240, "qiryat-shemona",33.0518934,35.4104844),
    SHMURAT_MARGALIYOT("שמורת מרגליות", 214240, "qiryat-shemona", 31.5475811,35.6301772),

    // galil tahton
    ALONI_ABA_VEBETLEHEM_GALILIT("אלוני אבא ובית לחם גלילית", 214236, "afula", 32.7292199, 35.1753594),
    HAR_TABOR("הר תבור", 214260, "dabburiya", 32.2322034,35.5736564),
    HAR_JONA("הר יונה", 214232, "nazareth", 32.7248074,35.3548361),
    KALANIT_BEMAHLEF_GOLANI("כלנית במחלף גולני", 214293, "ilaniyya", 32.6868916,35.5302972),
    NAHAL_TABOR("נחל תבור", 214236, "afula", 32.6524174,35.4806907),
    HAIN_SHARONA("עין שרונה", 214345, "sharona", 32.7193495,35.4957586),

    // heifa
    BAT_GALIM("בת גלים", 213181, "haifa", 32.8322852, 34.9872706),
    BATEI_HAKVAROT_HAISHANIM_BEHAIFA("בתי הקברות הישנים בחיפה", 213181, "haifa", 32.2859023, 35.4031059),
    GAN_HAHAIOT_HALIMUDI_BEHAIFA("גן החיות הלימודי בחיפה", 215837, "herzliyya", 32.8056641, 34.9882239),
    MALHAT_HAKISHON("מלחת הקישון", 214236, "afula", 32.8030455, 35.0303374),
    NAHAL_GIBORIM("נחל גיבורים", 213181, "haifa", 32.7969567, 35.0148303),
    NAHAL_ZIV_VENAHAL_REMEZ("נחל זיו ונחל רמז", 214336, "saar", 32.4101166, 35.4688453),
    NAHAL_SHIH("נחל שיח", 213181, "haifa", 32.2845801, 35.4031059),

    // kineret
    GAN_LEUMI_HOF_CORSI("גן לאומי חוף כורסי", 214268, "en-gev", 32.8261184, 35.6502168),
    GAN_LEUMI_HAMAT_TVERIA("גן לאומי חמת טבריה", 214243, "tiberias", 32.7660351, 35.551461),
    GAN_LEUMI_TEL_HODIM("גן לאומי תל הודים", 214316, "migdal", 32.8599, 35.53331),
    AIN_NON("עין נון", 214316, "migdal", 32.8411111, 35.5102778),
    AIN_SHEVA("עין שבע", 214376, "korazim", 32.8722392, 35.5587548),
    SHVIL_HASAKRIM("שביל הסכרים", 213181, "haifa", 32.6464464, 35.5732692),
    SHMURAT_BET_ZAIDA("שמורת בית ציידה", 1869831, "qazrin", 32.885841, 35.640993),

    // hermon
    BRIHAT_HAAIRUSIM("בריכת האירוסים", 212474, "netanya", 32.3855902,35.7663604),
    YAIR_SCHWITZ("יער שוויץ", 214243, "tiberias", 32.7733277, 35.5299201),
    MITZPAT_NIMROD_ROSH_PINA("מצפת נמרוד ראש פינה", 214241, "rosh-pinna", 32.9718883, 35.5510827),
    NAHAL_EL_AL("נחל אל על", 1869827, "natur", 32.8549597, 36.2721783),
    AIN_HAHERMON("עין החרמון", 214240, "qiryat-shemona", 33.4161617, 35.8570262),
    AIN_MUKASH("עין מוקש", 1869835, "ortal", 32.4333607, 35.7761778),
    HASHVIL_HATALYI_BEBENIAS("שביל התלוי בבניאס", 214363, "zuri%27el", 32.5082181, 36.3421405),

    // jerusalem
    GIVAT_HATORMUSIM("גבעת התורמוסים", 213251, "zafririm", 31.6820222,34.9741222),
    GIVAT_HATANAH("גבעת התנך", 213203, "emek-refa%27im", 31.7681412, 35.2252911),
    HAR_HABYTE("הר הבית", 261344, "jewich-quarter", 31.7781161, 35.2381814),
    HAR_HATSUFIM("הר הצופים", 261344, "jewich-quarter", 31.7930604,35.2449342),
    NAHAL_CARMILA_VENAHAL_CSALON("נחל כרמילה ונחל כסלון", 213230, "bet-shemesh", 31.784709, 35.030677),
    HIR_DAVID("עיר דוד", 213225, "jerusalem", 31.7742803, 35.2363998),
    TSUFA_VEASTAF("צובה והסטף", 213260, "beitar-illit", 31.771742, 35.1276556),

    // tel aviv
    BYTE_HAHIR("בית העיר", 215854, "tel-aviv", 32.0733795,34.7710266),
    BRICHOT_HAHOREF_BAHOULON("בריכות החורף בחולון", 212477, "bat-yam", 32.0735236, 34.8323125),
    MADRON_YAFO("מדרון יפו", 215753, "heiljafo", 32.0476177 ,34.7465972),
    PARK_HAYARKON("פארק הירקון", 215797, "ma%27oz-aviv", 32.100744,34.807026),
    PARK_WOLFSON("פארק וולפסון", 215771, "ramat-khen", 32.0580417, 34.8084888),
    PLORNTIN_VEMERCAZ_MISHARI("פלורנטין ומרכז מסחרי", 215854, "tel-aviv", 32.1392533,34.8876259),
    PRIHAT_NARKISIM_BAEZOR_PEE_GLILOT("פריחת נרקיסים באזור פי גלילות", 215854, "tel-aviv", 32.1384648, 34.8203027),

    // hashfela
    GAN_LEUMI_HULDA("גן לאומי חולדה", 212567, "pedaya", 32.4333607,35.7761778),
    HURVAT_MIDRAS("חורבת מדרס", 213251, "zafririm", 32.3727042, 36.3370389),
    YAIR_MALACHIM("יער המלאכים", 215702, "qiryat-gat", 32.3416148,36.3370392),
    MINZAR_LATRUN("מנזר לטרון", 212557, "nahshon", 32.4333607,35.7761778),
    PARK_ANBA("פארק ענבה", 213257, "newe-shalom", 32.4333607, 35.7761778),
    SHMURAT_LEEV("שמורת להב", 215735, "rahat", 32.2424693,36.3370402),
    SHMURAT_MAARAT_NESHER("שמורת מערת נשר", 213134, "en-hod", 32.4333607, 35.7761778),

    // eilat
    AKO_TIYULE_MIDBAR("אקו טיולי מדבר", 215615, "elat", 29.5528742,34.9512716),
    AKO_KEIF_TAYARUT_ECOLOGIT_KIBBUTZ_LOTAN("אקו-כיף תיירות אקולוגית קיבוץ לוטן", 215728, "qetura", 29.9868094, 35.0912209),
    HATAR_LEUMI_OM_RASHRASH("אתר לאומי אום רשרש", 305605, "durban", 29.54915 ,34.9555819),
    DEKELY_HAADOM_BEELAT("דקלי הדום באילת", 215688, "be%27er-ora", 29.6362832,34.9956309),
    PARK_HAZEPOROT_BEELAT("פארק הצפרות באילת", 1279264, "arrabah", 29.572824, 34.9724385),
    SCHMURAT_ITBATA("שמורת יטבתה", 215688, "be%27er-ora", 29.714114,35.1229088),
    SHMURAT_YAM_HAALMOGIM_BEELAT("שמורת ים האלמוגים באילת", 215615, "elat1", 29.5310456, 34.9698286),

    // ashdod
    GIVAT_YONA("גבעת יונה", 215613, "ashdod", 31.8139234,34.6556888),
    HOF_HAMITSUDA_ASHDOD("חוף המצודה אשדוד", 215613, "ashdod", 31.7817686, 34.6223983),
    HOF_MEI_AMI("חוף מי עמי", 215613, "ashdod", 31.8126397 ,34.6410983),
    HOF_PALMACHIM("חוף פלמחים", 215613, "ashdod", 31.9252887,34.698873),
    YAIR_HERUBIT_VETEL_ZAFIT("יער חרובית ותל צפית", 215644, "kefar-menahem", 31.7270593, 34.8606421),
    PARK_NAHAL_LAHISH("פארק נחל לכיש", 214625, "orta-san-giulio", 31.816631,34.6515931),

    // ashkelon
    ATRAKTSYOT_HAWAT_PHILIP("אטרקציות חוות פיליפ", 215689, "bet-qama", 31.5198238,34.7784843),
    BATRUNOT_BAARI("בתרונות בארי", 214964, "bari", 31.4397723, 34.4952073),
    BATRUNOT_RUKHMA("בתרונות רוחמה", 215689, "bet-qama", 31.4855921 ,34.754277),
    GIVAT_TOM_VETOMER("גבעת תום ותומר", 215697, "negba", 31.6698015,34.6787787),
    HANYON_LAYLA_GAN_LEUMI_ASHKELON("חניון לילה גן לאומי אשקלון", 213157, "newe-yam", 31.6624815, 34.5485557),
    NAHAL_HABISHOR_VEPARK_ESHKOL("נחל הבשור ופארק אשכול", 215645, "magen", 31.3083946,34.4917817),
    PINAT_HIGH_GIVRAAM("פינת חי גברעם", 215634, "gevaram", 31.5925754,34.6153574),

    // hanegev
    AHUZAT_CAVAR_BEN_GURION("אחוזת קבר בן גוריון", 215704, "sede-boqer", 30.847961,34.782144),
    GAV_YALAK("גב ילק", 215620, "mizpe-ramon", 30.5671497, 34.9036568),
    HAR_KRUM_VEHAR_ARIF("הר כרכום והר עריף", 215620, "mizpe-ramon", 30.2861288,34.753477),
    HAWAT_HAALPACOT("חוות האלפקות", 215699, "nizzana", 30.6105355,34.7791744),
    MITZPA_KOHAVIM_NAYAD("מצפה כוכבים נייד", 215620, "mizpe-ramon", 30.5666143, 34.8915482),
    NAHAL_AKRABIM("נחל עקרבים", 215737, "idan", 30.9120507,35.1873523),
    AIN_AVDAT_VEIR_HANABATEIM_AVDAT("עין עבדת ועיר הנבטים עבדת", 215704, "sede-boqer", 30.8242569,34.762792),

    // yam hamelah
    GAN_LEUMI_KUMRAN("גן לאומי קומראן", 215707, "yeroham", 31.7412525,35.4569593),
    HOF_KALI("חוף קליה", 1279200, "bethlehem", 31.7630814, 35.5018111),
    KFAR_HANUKADIM("כפר הנוקדים", 215686, "arad", 31.305785 ,35.269544),
    NAHAL_PARAS_TACHTON("נחל פרס תחתון", 215650, "ne%27ot-hakikar", 31.0136658,35.2539682),
    AIN_MAVO("עין מבוע", 212569, "qidron", 31.8385808, 35.3556443),
    AIN_NAMIER("עין נמר", 215691, "en-gedi", 31.3498212,35.3037225),
    AIN_KEDEM("עין קדם", 215697, "negba", 31.4939033,35.3511771);

    private final String title;
    private final int accuweatherCode;
    private final String accuweatherName;
    private final double longitude;
    private final double latitude;

    eSite(String title, int accuweatherCode, String accuweatherName, double longitude, double latitude) {
        this.title = title;
        this.accuweatherCode = accuweatherCode;
        this.accuweatherName = accuweatherName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public int getAccuweatherCode() {
        return accuweatherCode;
    }

    public String getAccuweatherName() {
        return accuweatherName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public static List<String> getTitles(List<eSite> sites) {
        List<String> titles = new ArrayList<>();

        for(eSite site : sites) {
            titles.add(site.getTitle());
        }

        return titles;
    }
}
