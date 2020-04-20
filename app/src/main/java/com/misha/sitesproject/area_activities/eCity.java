package com.misha.sitesproject.area_activities;

import com.misha.sitesproject.sites_activities.eSite;

public enum eCity {
    RAMAT_HAGOLAN("רמת הגולן",
            new eSite[] {eSite.HAKFAR_BEKATSE_HAAR, eSite.YAIR_ADOM, eSite.MIZAD_ATRAT, eSite.AIN_KSHATOT,
                eSite.AIN_SHOKO, eSite.RISER_HAZAFON, eSite.SHMURAT_BRIHAT_BERAON}),
    HAGALIL_HAELYON("הגליל העליון",
            new eSite[] {eSite.ROSH_HANIKRA, eSite.HAR_HASHFANIM, eSite.NAHAL_ZALMON,
                eSite.MAARAT_PAAR, eSite.SHMURAT_NAHAL_DISHON, eSite.SHMURAT_BREICHAT_DOVEV, eSite.SHMURAT_MARGALIYOT}),
    HAGALIL_HATACHTON("הגליל התחתון",
            new eSite[] {eSite.ALONI_ABA_VEBETLEHEM_GALILIT, eSite.HAR_TABOR, eSite.HAR_JONA,
                eSite.KALANIT_BEMAHLEF_GOLANI, eSite.NAHAL_TABOR, eSite.HAIN_SHARONA}),
    HAIFA("חיפה",
            new eSite[] {eSite.BAT_GALIM, eSite.BATEI_HAKVAROT_HAISHANIM_BEHAIFA, eSite.GAN_HAHAIOT_HALIMUDI_BEHAIFA,
                eSite.MALHAT_HAKISHON, eSite.NAHAL_GIBORIM, eSite.NAHAL_ZIV_VENAHAL_REMEZ, eSite.NAHAL_SHIH}),
    HAKINERET( "הכינרת",
            new eSite[] {eSite.GAN_LEUMI_HOF_CORSI, eSite.GAN_LEUMI_HAMAT_TVERIA, eSite.GAN_LEUMI_TEL_HODIM,
                eSite.AIN_NON, eSite.AIN_SHEVA, eSite.SHVIL_HASAKRIM, eSite.SHMURAT_BET_ZAIDA} ),
    HERMON("חרמון",
            new eSite[] {eSite.BRIHAT_HAAIRUSIM, eSite.YAIR_SCHWITZ, eSite.MITZPAT_NIMROD_ROSH_PINA,
                eSite.NAHAL_EL_AL, eSite.AIN_HAHERMON, eSite.AIN_MUKASH, eSite.HASHVIL_HATALYI_BEBENIAS}),
    JERUSALEM("ירושלים",
            new eSite[] {eSite.GIVAT_HATORMUSIM, eSite.GIVAT_HATANAH, eSite.HAR_HABYTE, eSite.HAR_HATSUFIM,
                eSite.NAHAL_CARMILA_VENAHAL_CSALON, eSite.HIR_DAVID, eSite.TSUFA_VEASTAF}),
    TEL_AVIV("תל אביב",
            new eSite[] {eSite.BYTE_HAHIR, eSite.BRICHOT_HAHOREF_BAHOULON, eSite.MADRON_YAFO,
                eSite.PARK_HAYARKON, eSite.PARK_WOLFSON, eSite.PARK_WOLFSON, eSite.PLORNTIN_VEMERCAZ_MISHARI,
                eSite.PRIHAT_NARKISIM_BAEZOR_PEE_GLILOT}),
    HASHFELA("השפלה",
            new eSite[] {eSite.GAN_LEUMI_HULDA, eSite.HURVAT_MIDRAS, eSite.YAIR_MALACHIM,
                eSite.MINZAR_LATRUN, eSite.PARK_ANBA, eSite.SHMURAT_LEEV, eSite.SHMURAT_MAARAT_NESHER}),
    EILAT("אילת",
            new eSite[] {eSite.AKO_TIYULE_MIDBAR, eSite.AKO_KEIF_TAYARUT_ECOLOGIT_KIBBUTZ_LOTAN, eSite.HATAR_LEUMI_OM_RASHRASH,
                eSite.DEKELY_HAADOM_BEELAT, eSite.PARK_HAZEPOROT_BEELAT, eSite.SCHMURAT_ITBATA, eSite.SHMURAT_YAM_HAALMOGIM_BEELAT}),
    ASHDOD("אשדוד",
            new eSite[] {eSite.GIVAT_YONA, eSite.HOF_HAMITSUDA_ASHDOD, eSite.HOF_MEI_AMI, eSite.HOF_PALMACHIM,
                eSite.HOF_PALMACHIM, eSite.HOF_PALMACHIM, eSite.PARK_NAHAL_LAHISH, eSite.YAIR_HERUBIT_VETEL_ZAFIT}),
    ASHKELON("אשקלון",
            new eSite[] {eSite.ATRAKTSYOT_HAWAT_PHILIP, eSite.BATRUNOT_BAARI, eSite.BATRUNOT_RUKHMA, eSite.GIVAT_TOM_VETOMER,
                eSite.HANYON_LAYLA_GAN_LEUMI_ASHKELON, eSite.NAHAL_HABISHOR_VEPARK_ESHKOL, eSite.PINAT_HIGH_GIVRAAM}),
    HANEGEV("הנגב",
            new eSite[] {eSite.AHUZAT_CAVAR_BEN_GURION, eSite.GAV_YALAK, eSite.HAR_KRUM_VEHAR_ARIF, eSite.HAWAT_HAALPACOT,
                eSite.MITZPA_KOHAVIM_NAYAD, eSite.NAHAL_AKRABIM, eSite.AIN_AVDAT_VEIR_HANABATEIM_AVDAT}),
    YAM_HAMELAH("ים המלח",
            new eSite[] {eSite.GAN_LEUMI_KUMRAN, eSite.HOF_KALI, eSite.KFAR_HANUKADIM, eSite.NAHAL_PARAS_TACHTON,
                eSite.AIN_MAVO, eSite.AIN_NAMIER, eSite.AIN_KEDEM});

    private final String title;
    private final eSite[] sites;

    eCity(String title, eSite[] sites) {
        this.title = title;
        this.sites = sites;
    }

    eCity(String title) {
        this.title = title;
        this.sites = new eSite[] {};
    }

    public String getTitle() {
        return title;
    }

    public eSite[] getSites() {
        return sites;
    }

    public static String[] getTitles() {
        return getTitles(eCity.values());
    }

    public static String[] getTitles(eCity[] cities) {
        String[] titles = new String[cities.length];

        for(int i = 0; i < titles.length; i++) {
            titles[i] = cities[i].getTitle();
        }

        return titles;
    }
}
