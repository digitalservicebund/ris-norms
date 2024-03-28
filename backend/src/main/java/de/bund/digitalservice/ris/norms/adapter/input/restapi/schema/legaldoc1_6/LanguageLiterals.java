package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for languageLiterals.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="languageLiterals"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="abk"/&gt;
 *     &lt;enumeration value="aar"/&gt;
 *     &lt;enumeration value="afr"/&gt;
 *     &lt;enumeration value="aka"/&gt;
 *     &lt;enumeration value="sqi"/&gt;
 *     &lt;enumeration value="amh"/&gt;
 *     &lt;enumeration value="ara"/&gt;
 *     &lt;enumeration value="arg"/&gt;
 *     &lt;enumeration value="hye"/&gt;
 *     &lt;enumeration value="asm"/&gt;
 *     &lt;enumeration value="ava"/&gt;
 *     &lt;enumeration value="ave"/&gt;
 *     &lt;enumeration value="aym"/&gt;
 *     &lt;enumeration value="aze"/&gt;
 *     &lt;enumeration value="bam"/&gt;
 *     &lt;enumeration value="bak"/&gt;
 *     &lt;enumeration value="eus"/&gt;
 *     &lt;enumeration value="bel"/&gt;
 *     &lt;enumeration value="ben"/&gt;
 *     &lt;enumeration value="bis"/&gt;
 *     &lt;enumeration value="bos"/&gt;
 *     &lt;enumeration value="bre"/&gt;
 *     &lt;enumeration value="bul"/&gt;
 *     &lt;enumeration value="mya"/&gt;
 *     &lt;enumeration value="cat"/&gt;
 *     &lt;enumeration value="cha"/&gt;
 *     &lt;enumeration value="che"/&gt;
 *     &lt;enumeration value="nya"/&gt;
 *     &lt;enumeration value="zho"/&gt;
 *     &lt;enumeration value="chu"/&gt;
 *     &lt;enumeration value="chv"/&gt;
 *     &lt;enumeration value="cor"/&gt;
 *     &lt;enumeration value="cos"/&gt;
 *     &lt;enumeration value="cre"/&gt;
 *     &lt;enumeration value="hrv"/&gt;
 *     &lt;enumeration value="ces"/&gt;
 *     &lt;enumeration value="dan"/&gt;
 *     &lt;enumeration value="div"/&gt;
 *     &lt;enumeration value="nld"/&gt;
 *     &lt;enumeration value="dzo"/&gt;
 *     &lt;enumeration value="eng"/&gt;
 *     &lt;enumeration value="epo"/&gt;
 *     &lt;enumeration value="est"/&gt;
 *     &lt;enumeration value="ewe"/&gt;
 *     &lt;enumeration value="fao"/&gt;
 *     &lt;enumeration value="fij"/&gt;
 *     &lt;enumeration value="fin"/&gt;
 *     &lt;enumeration value="fra"/&gt;
 *     &lt;enumeration value="fry"/&gt;
 *     &lt;enumeration value="ful"/&gt;
 *     &lt;enumeration value="gla"/&gt;
 *     &lt;enumeration value="glg"/&gt;
 *     &lt;enumeration value="lug"/&gt;
 *     &lt;enumeration value="kat"/&gt;
 *     &lt;enumeration value="deu"/&gt;
 *     &lt;enumeration value="ell"/&gt;
 *     &lt;enumeration value="kal"/&gt;
 *     &lt;enumeration value="grn"/&gt;
 *     &lt;enumeration value="guj"/&gt;
 *     &lt;enumeration value="hat"/&gt;
 *     &lt;enumeration value="hau"/&gt;
 *     &lt;enumeration value="heb"/&gt;
 *     &lt;enumeration value="her"/&gt;
 *     &lt;enumeration value="hin"/&gt;
 *     &lt;enumeration value="hmo"/&gt;
 *     &lt;enumeration value="hun"/&gt;
 *     &lt;enumeration value="isl"/&gt;
 *     &lt;enumeration value="ido"/&gt;
 *     &lt;enumeration value="ibo"/&gt;
 *     &lt;enumeration value="ind"/&gt;
 *     &lt;enumeration value="ina"/&gt;
 *     &lt;enumeration value="ile"/&gt;
 *     &lt;enumeration value="iku"/&gt;
 *     &lt;enumeration value="ipk"/&gt;
 *     &lt;enumeration value="gle"/&gt;
 *     &lt;enumeration value="ita"/&gt;
 *     &lt;enumeration value="jpn"/&gt;
 *     &lt;enumeration value="jav"/&gt;
 *     &lt;enumeration value="kan"/&gt;
 *     &lt;enumeration value="kau"/&gt;
 *     &lt;enumeration value="kas"/&gt;
 *     &lt;enumeration value="kaz"/&gt;
 *     &lt;enumeration value="khm"/&gt;
 *     &lt;enumeration value="kik"/&gt;
 *     &lt;enumeration value="kin"/&gt;
 *     &lt;enumeration value="kir"/&gt;
 *     &lt;enumeration value="kom"/&gt;
 *     &lt;enumeration value="kon"/&gt;
 *     &lt;enumeration value="kor"/&gt;
 *     &lt;enumeration value="kua"/&gt;
 *     &lt;enumeration value="kur"/&gt;
 *     &lt;enumeration value="lao"/&gt;
 *     &lt;enumeration value="lat"/&gt;
 *     &lt;enumeration value="lav"/&gt;
 *     &lt;enumeration value="lim"/&gt;
 *     &lt;enumeration value="lin"/&gt;
 *     &lt;enumeration value="lit"/&gt;
 *     &lt;enumeration value="lub"/&gt;
 *     &lt;enumeration value="ltz"/&gt;
 *     &lt;enumeration value="mkd"/&gt;
 *     &lt;enumeration value="mlg"/&gt;
 *     &lt;enumeration value="msa"/&gt;
 *     &lt;enumeration value="mal"/&gt;
 *     &lt;enumeration value="mlt"/&gt;
 *     &lt;enumeration value="glv"/&gt;
 *     &lt;enumeration value="mri"/&gt;
 *     &lt;enumeration value="mar"/&gt;
 *     &lt;enumeration value="mah"/&gt;
 *     &lt;enumeration value="mon"/&gt;
 *     &lt;enumeration value="nau"/&gt;
 *     &lt;enumeration value="nav"/&gt;
 *     &lt;enumeration value="nde"/&gt;
 *     &lt;enumeration value="nbl"/&gt;
 *     &lt;enumeration value="ndo"/&gt;
 *     &lt;enumeration value="nep"/&gt;
 *     &lt;enumeration value="nor"/&gt;
 *     &lt;enumeration value="nob"/&gt;
 *     &lt;enumeration value="nno"/&gt;
 *     &lt;enumeration value="iii"/&gt;
 *     &lt;enumeration value="oci"/&gt;
 *     &lt;enumeration value="oji"/&gt;
 *     &lt;enumeration value="ori"/&gt;
 *     &lt;enumeration value="orm"/&gt;
 *     &lt;enumeration value="oss"/&gt;
 *     &lt;enumeration value="pli"/&gt;
 *     &lt;enumeration value="pus"/&gt;
 *     &lt;enumeration value="fas"/&gt;
 *     &lt;enumeration value="pol"/&gt;
 *     &lt;enumeration value="por"/&gt;
 *     &lt;enumeration value="pan"/&gt;
 *     &lt;enumeration value="que"/&gt;
 *     &lt;enumeration value="ron"/&gt;
 *     &lt;enumeration value="roh"/&gt;
 *     &lt;enumeration value="run"/&gt;
 *     &lt;enumeration value="rus"/&gt;
 *     &lt;enumeration value="sme"/&gt;
 *     &lt;enumeration value="smo"/&gt;
 *     &lt;enumeration value="sag"/&gt;
 *     &lt;enumeration value="san"/&gt;
 *     &lt;enumeration value="srd"/&gt;
 *     &lt;enumeration value="srp"/&gt;
 *     &lt;enumeration value="sna"/&gt;
 *     &lt;enumeration value="snd"/&gt;
 *     &lt;enumeration value="sin"/&gt;
 *     &lt;enumeration value="slk"/&gt;
 *     &lt;enumeration value="slv"/&gt;
 *     &lt;enumeration value="som"/&gt;
 *     &lt;enumeration value="sot"/&gt;
 *     &lt;enumeration value="spa"/&gt;
 *     &lt;enumeration value="sun"/&gt;
 *     &lt;enumeration value="swa"/&gt;
 *     &lt;enumeration value="ssw"/&gt;
 *     &lt;enumeration value="swe"/&gt;
 *     &lt;enumeration value="tgl"/&gt;
 *     &lt;enumeration value="tah"/&gt;
 *     &lt;enumeration value="tgk"/&gt;
 *     &lt;enumeration value="tam"/&gt;
 *     &lt;enumeration value="tat"/&gt;
 *     &lt;enumeration value="tel"/&gt;
 *     &lt;enumeration value="tha"/&gt;
 *     &lt;enumeration value="bod"/&gt;
 *     &lt;enumeration value="tir"/&gt;
 *     &lt;enumeration value="ton"/&gt;
 *     &lt;enumeration value="tso"/&gt;
 *     &lt;enumeration value="tsn"/&gt;
 *     &lt;enumeration value="tur"/&gt;
 *     &lt;enumeration value="tuk"/&gt;
 *     &lt;enumeration value="twi"/&gt;
 *     &lt;enumeration value="uig"/&gt;
 *     &lt;enumeration value="ukr"/&gt;
 *     &lt;enumeration value="urd"/&gt;
 *     &lt;enumeration value="uzb"/&gt;
 *     &lt;enumeration value="ven"/&gt;
 *     &lt;enumeration value="vie"/&gt;
 *     &lt;enumeration value="vol"/&gt;
 *     &lt;enumeration value="wln"/&gt;
 *     &lt;enumeration value="cym"/&gt;
 *     &lt;enumeration value="wol"/&gt;
 *     &lt;enumeration value="xho"/&gt;
 *     &lt;enumeration value="yid"/&gt;
 *     &lt;enumeration value="yor"/&gt;
 *     &lt;enumeration value="zha"/&gt;
 *     &lt;enumeration value="zul"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "languageLiterals", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum LanguageLiterals {
  @XmlEnumValue("abk")
  ABK("abk"),
  @XmlEnumValue("aar")
  AAR("aar"),
  @XmlEnumValue("afr")
  AFR("afr"),
  @XmlEnumValue("aka")
  AKA("aka"),
  @XmlEnumValue("sqi")
  SQI("sqi"),
  @XmlEnumValue("amh")
  AMH("amh"),
  @XmlEnumValue("ara")
  ARA("ara"),
  @XmlEnumValue("arg")
  ARG("arg"),
  @XmlEnumValue("hye")
  HYE("hye"),
  @XmlEnumValue("asm")
  ASM("asm"),
  @XmlEnumValue("ava")
  AVA("ava"),
  @XmlEnumValue("ave")
  AVE("ave"),
  @XmlEnumValue("aym")
  AYM("aym"),
  @XmlEnumValue("aze")
  AZE("aze"),
  @XmlEnumValue("bam")
  BAM("bam"),
  @XmlEnumValue("bak")
  BAK("bak"),
  @XmlEnumValue("eus")
  EUS("eus"),
  @XmlEnumValue("bel")
  BEL("bel"),
  @XmlEnumValue("ben")
  BEN("ben"),
  @XmlEnumValue("bis")
  BIS("bis"),
  @XmlEnumValue("bos")
  BOS("bos"),
  @XmlEnumValue("bre")
  BRE("bre"),
  @XmlEnumValue("bul")
  BUL("bul"),
  @XmlEnumValue("mya")
  MYA("mya"),
  @XmlEnumValue("cat")
  CAT("cat"),
  @XmlEnumValue("cha")
  CHA("cha"),
  @XmlEnumValue("che")
  CHE("che"),
  @XmlEnumValue("nya")
  NYA("nya"),
  @XmlEnumValue("zho")
  ZHO("zho"),
  @XmlEnumValue("chu")
  CHU("chu"),
  @XmlEnumValue("chv")
  CHV("chv"),
  @XmlEnumValue("cor")
  COR("cor"),
  @XmlEnumValue("cos")
  COS("cos"),
  @XmlEnumValue("cre")
  CRE("cre"),
  @XmlEnumValue("hrv")
  HRV("hrv"),
  @XmlEnumValue("ces")
  CES("ces"),
  @XmlEnumValue("dan")
  DAN("dan"),
  @XmlEnumValue("div")
  DIV("div"),
  @XmlEnumValue("nld")
  NLD("nld"),
  @XmlEnumValue("dzo")
  DZO("dzo"),
  @XmlEnumValue("eng")
  ENG("eng"),
  @XmlEnumValue("epo")
  EPO("epo"),
  @XmlEnumValue("est")
  EST("est"),
  @XmlEnumValue("ewe")
  EWE("ewe"),
  @XmlEnumValue("fao")
  FAO("fao"),
  @XmlEnumValue("fij")
  FIJ("fij"),
  @XmlEnumValue("fin")
  FIN("fin"),
  @XmlEnumValue("fra")
  FRA("fra"),
  @XmlEnumValue("fry")
  FRY("fry"),
  @XmlEnumValue("ful")
  FUL("ful"),
  @XmlEnumValue("gla")
  GLA("gla"),
  @XmlEnumValue("glg")
  GLG("glg"),
  @XmlEnumValue("lug")
  LUG("lug"),
  @XmlEnumValue("kat")
  KAT("kat"),
  @XmlEnumValue("deu")
  DEU("deu"),
  @XmlEnumValue("ell")
  ELL("ell"),
  @XmlEnumValue("kal")
  KAL("kal"),
  @XmlEnumValue("grn")
  GRN("grn"),
  @XmlEnumValue("guj")
  GUJ("guj"),
  @XmlEnumValue("hat")
  HAT("hat"),
  @XmlEnumValue("hau")
  HAU("hau"),
  @XmlEnumValue("heb")
  HEB("heb"),
  @XmlEnumValue("her")
  HER("her"),
  @XmlEnumValue("hin")
  HIN("hin"),
  @XmlEnumValue("hmo")
  HMO("hmo"),
  @XmlEnumValue("hun")
  HUN("hun"),
  @XmlEnumValue("isl")
  ISL("isl"),
  @XmlEnumValue("ido")
  IDO("ido"),
  @XmlEnumValue("ibo")
  IBO("ibo"),
  @XmlEnumValue("ind")
  IND("ind"),
  @XmlEnumValue("ina")
  INA("ina"),
  @XmlEnumValue("ile")
  ILE("ile"),
  @XmlEnumValue("iku")
  IKU("iku"),
  @XmlEnumValue("ipk")
  IPK("ipk"),
  @XmlEnumValue("gle")
  GLE("gle"),
  @XmlEnumValue("ita")
  ITA("ita"),
  @XmlEnumValue("jpn")
  JPN("jpn"),
  @XmlEnumValue("jav")
  JAV("jav"),
  @XmlEnumValue("kan")
  KAN("kan"),
  @XmlEnumValue("kau")
  KAU("kau"),
  @XmlEnumValue("kas")
  KAS("kas"),
  @XmlEnumValue("kaz")
  KAZ("kaz"),
  @XmlEnumValue("khm")
  KHM("khm"),
  @XmlEnumValue("kik")
  KIK("kik"),
  @XmlEnumValue("kin")
  KIN("kin"),
  @XmlEnumValue("kir")
  KIR("kir"),
  @XmlEnumValue("kom")
  KOM("kom"),
  @XmlEnumValue("kon")
  KON("kon"),
  @XmlEnumValue("kor")
  KOR("kor"),
  @XmlEnumValue("kua")
  KUA("kua"),
  @XmlEnumValue("kur")
  KUR("kur"),
  @XmlEnumValue("lao")
  LAO("lao"),
  @XmlEnumValue("lat")
  LAT("lat"),
  @XmlEnumValue("lav")
  LAV("lav"),
  @XmlEnumValue("lim")
  LIM("lim"),
  @XmlEnumValue("lin")
  LIN("lin"),
  @XmlEnumValue("lit")
  LIT("lit"),
  @XmlEnumValue("lub")
  LUB("lub"),
  @XmlEnumValue("ltz")
  LTZ("ltz"),
  @XmlEnumValue("mkd")
  MKD("mkd"),
  @XmlEnumValue("mlg")
  MLG("mlg"),
  @XmlEnumValue("msa")
  MSA("msa"),
  @XmlEnumValue("mal")
  MAL("mal"),
  @XmlEnumValue("mlt")
  MLT("mlt"),
  @XmlEnumValue("glv")
  GLV("glv"),
  @XmlEnumValue("mri")
  MRI("mri"),
  @XmlEnumValue("mar")
  MAR("mar"),
  @XmlEnumValue("mah")
  MAH("mah"),
  @XmlEnumValue("mon")
  MON("mon"),
  @XmlEnumValue("nau")
  NAU("nau"),
  @XmlEnumValue("nav")
  NAV("nav"),
  @XmlEnumValue("nde")
  NDE("nde"),
  @XmlEnumValue("nbl")
  NBL("nbl"),
  @XmlEnumValue("ndo")
  NDO("ndo"),
  @XmlEnumValue("nep")
  NEP("nep"),
  @XmlEnumValue("nor")
  NOR("nor"),
  @XmlEnumValue("nob")
  NOB("nob"),
  @XmlEnumValue("nno")
  NNO("nno"),
  @XmlEnumValue("iii")
  III("iii"),
  @XmlEnumValue("oci")
  OCI("oci"),
  @XmlEnumValue("oji")
  OJI("oji"),
  @XmlEnumValue("ori")
  ORI("ori"),
  @XmlEnumValue("orm")
  ORM("orm"),
  @XmlEnumValue("oss")
  OSS("oss"),
  @XmlEnumValue("pli")
  PLI("pli"),
  @XmlEnumValue("pus")
  PUS("pus"),
  @XmlEnumValue("fas")
  FAS("fas"),
  @XmlEnumValue("pol")
  POL("pol"),
  @XmlEnumValue("por")
  POR("por"),
  @XmlEnumValue("pan")
  PAN("pan"),
  @XmlEnumValue("que")
  QUE("que"),
  @XmlEnumValue("ron")
  RON("ron"),
  @XmlEnumValue("roh")
  ROH("roh"),
  @XmlEnumValue("run")
  RUN("run"),
  @XmlEnumValue("rus")
  RUS("rus"),
  @XmlEnumValue("sme")
  SME("sme"),
  @XmlEnumValue("smo")
  SMO("smo"),
  @XmlEnumValue("sag")
  SAG("sag"),
  @XmlEnumValue("san")
  SAN("san"),
  @XmlEnumValue("srd")
  SRD("srd"),
  @XmlEnumValue("srp")
  SRP("srp"),
  @XmlEnumValue("sna")
  SNA("sna"),
  @XmlEnumValue("snd")
  SND("snd"),
  @XmlEnumValue("sin")
  SIN("sin"),
  @XmlEnumValue("slk")
  SLK("slk"),
  @XmlEnumValue("slv")
  SLV("slv"),
  @XmlEnumValue("som")
  SOM("som"),
  @XmlEnumValue("sot")
  SOT("sot"),
  @XmlEnumValue("spa")
  SPA("spa"),
  @XmlEnumValue("sun")
  SUN("sun"),
  @XmlEnumValue("swa")
  SWA("swa"),
  @XmlEnumValue("ssw")
  SSW("ssw"),
  @XmlEnumValue("swe")
  SWE("swe"),
  @XmlEnumValue("tgl")
  TGL("tgl"),
  @XmlEnumValue("tah")
  TAH("tah"),
  @XmlEnumValue("tgk")
  TGK("tgk"),
  @XmlEnumValue("tam")
  TAM("tam"),
  @XmlEnumValue("tat")
  TAT("tat"),
  @XmlEnumValue("tel")
  TEL("tel"),
  @XmlEnumValue("tha")
  THA("tha"),
  @XmlEnumValue("bod")
  BOD("bod"),
  @XmlEnumValue("tir")
  TIR("tir"),
  @XmlEnumValue("ton")
  TON("ton"),
  @XmlEnumValue("tso")
  TSO("tso"),
  @XmlEnumValue("tsn")
  TSN("tsn"),
  @XmlEnumValue("tur")
  TUR("tur"),
  @XmlEnumValue("tuk")
  TUK("tuk"),
  @XmlEnumValue("twi")
  TWI("twi"),
  @XmlEnumValue("uig")
  UIG("uig"),
  @XmlEnumValue("ukr")
  UKR("ukr"),
  @XmlEnumValue("urd")
  URD("urd"),
  @XmlEnumValue("uzb")
  UZB("uzb"),
  @XmlEnumValue("ven")
  VEN("ven"),
  @XmlEnumValue("vie")
  VIE("vie"),
  @XmlEnumValue("vol")
  VOL("vol"),
  @XmlEnumValue("wln")
  WLN("wln"),
  @XmlEnumValue("cym")
  CYM("cym"),
  @XmlEnumValue("wol")
  WOL("wol"),
  @XmlEnumValue("xho")
  XHO("xho"),
  @XmlEnumValue("yid")
  YID("yid"),
  @XmlEnumValue("yor")
  YOR("yor"),
  @XmlEnumValue("zha")
  ZHA("zha"),
  @XmlEnumValue("zul")
  ZUL("zul");
  private final String value;

  LanguageLiterals(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static LanguageLiterals fromValue(String v) {
    for (LanguageLiterals c : LanguageLiterals.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
