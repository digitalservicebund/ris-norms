package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.legaldoc1_6;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for countryLiterals.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="countryLiterals"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ad"/&gt;
 *     &lt;enumeration value="ae"/&gt;
 *     &lt;enumeration value="af"/&gt;
 *     &lt;enumeration value="ag"/&gt;
 *     &lt;enumeration value="ai"/&gt;
 *     &lt;enumeration value="al"/&gt;
 *     &lt;enumeration value="am"/&gt;
 *     &lt;enumeration value="ao"/&gt;
 *     &lt;enumeration value="aq"/&gt;
 *     &lt;enumeration value="ar"/&gt;
 *     &lt;enumeration value="as"/&gt;
 *     &lt;enumeration value="at"/&gt;
 *     &lt;enumeration value="au"/&gt;
 *     &lt;enumeration value="aw"/&gt;
 *     &lt;enumeration value="ax"/&gt;
 *     &lt;enumeration value="az"/&gt;
 *     &lt;enumeration value="ba"/&gt;
 *     &lt;enumeration value="bb"/&gt;
 *     &lt;enumeration value="bd"/&gt;
 *     &lt;enumeration value="be"/&gt;
 *     &lt;enumeration value="bf"/&gt;
 *     &lt;enumeration value="bg"/&gt;
 *     &lt;enumeration value="bh"/&gt;
 *     &lt;enumeration value="bi"/&gt;
 *     &lt;enumeration value="bj"/&gt;
 *     &lt;enumeration value="bl"/&gt;
 *     &lt;enumeration value="bm"/&gt;
 *     &lt;enumeration value="bn"/&gt;
 *     &lt;enumeration value="bo"/&gt;
 *     &lt;enumeration value="br"/&gt;
 *     &lt;enumeration value="bs"/&gt;
 *     &lt;enumeration value="bt"/&gt;
 *     &lt;enumeration value="bv"/&gt;
 *     &lt;enumeration value="bw"/&gt;
 *     &lt;enumeration value="by"/&gt;
 *     &lt;enumeration value="bz"/&gt;
 *     &lt;enumeration value="ca"/&gt;
 *     &lt;enumeration value="cc"/&gt;
 *     &lt;enumeration value="cd"/&gt;
 *     &lt;enumeration value="cf"/&gt;
 *     &lt;enumeration value="cg"/&gt;
 *     &lt;enumeration value="ch"/&gt;
 *     &lt;enumeration value="ci"/&gt;
 *     &lt;enumeration value="ck"/&gt;
 *     &lt;enumeration value="cl"/&gt;
 *     &lt;enumeration value="cm"/&gt;
 *     &lt;enumeration value="cn"/&gt;
 *     &lt;enumeration value="co"/&gt;
 *     &lt;enumeration value="cp"/&gt;
 *     &lt;enumeration value="cr"/&gt;
 *     &lt;enumeration value="cu"/&gt;
 *     &lt;enumeration value="cv"/&gt;
 *     &lt;enumeration value="cw"/&gt;
 *     &lt;enumeration value="cx"/&gt;
 *     &lt;enumeration value="cy"/&gt;
 *     &lt;enumeration value="cz"/&gt;
 *     &lt;enumeration value="de"/&gt;
 *     &lt;enumeration value="dj"/&gt;
 *     &lt;enumeration value="dk"/&gt;
 *     &lt;enumeration value="dm"/&gt;
 *     &lt;enumeration value="do"/&gt;
 *     &lt;enumeration value="dz"/&gt;
 *     &lt;enumeration value="ec"/&gt;
 *     &lt;enumeration value="ee"/&gt;
 *     &lt;enumeration value="eg"/&gt;
 *     &lt;enumeration value="eh"/&gt;
 *     &lt;enumeration value="el"/&gt;
 *     &lt;enumeration value="er"/&gt;
 *     &lt;enumeration value="es"/&gt;
 *     &lt;enumeration value="et"/&gt;
 *     &lt;enumeration value="fi"/&gt;
 *     &lt;enumeration value="fj"/&gt;
 *     &lt;enumeration value="fk"/&gt;
 *     &lt;enumeration value="fm"/&gt;
 *     &lt;enumeration value="fo"/&gt;
 *     &lt;enumeration value="fr"/&gt;
 *     &lt;enumeration value="ga"/&gt;
 *     &lt;enumeration value="gd"/&gt;
 *     &lt;enumeration value="ge"/&gt;
 *     &lt;enumeration value="gf"/&gt;
 *     &lt;enumeration value="gg"/&gt;
 *     &lt;enumeration value="gh"/&gt;
 *     &lt;enumeration value="gi"/&gt;
 *     &lt;enumeration value="gl"/&gt;
 *     &lt;enumeration value="gm"/&gt;
 *     &lt;enumeration value="gn"/&gt;
 *     &lt;enumeration value="gp"/&gt;
 *     &lt;enumeration value="gq"/&gt;
 *     &lt;enumeration value="gs"/&gt;
 *     &lt;enumeration value="gt"/&gt;
 *     &lt;enumeration value="gu"/&gt;
 *     &lt;enumeration value="gw"/&gt;
 *     &lt;enumeration value="gy"/&gt;
 *     &lt;enumeration value="hk"/&gt;
 *     &lt;enumeration value="hm"/&gt;
 *     &lt;enumeration value="hn"/&gt;
 *     &lt;enumeration value="hr"/&gt;
 *     &lt;enumeration value="ht"/&gt;
 *     &lt;enumeration value="hu"/&gt;
 *     &lt;enumeration value="id"/&gt;
 *     &lt;enumeration value="ie"/&gt;
 *     &lt;enumeration value="il"/&gt;
 *     &lt;enumeration value="im"/&gt;
 *     &lt;enumeration value="in"/&gt;
 *     &lt;enumeration value="io"/&gt;
 *     &lt;enumeration value="iq"/&gt;
 *     &lt;enumeration value="ir"/&gt;
 *     &lt;enumeration value="is"/&gt;
 *     &lt;enumeration value="it"/&gt;
 *     &lt;enumeration value="je"/&gt;
 *     &lt;enumeration value="jm"/&gt;
 *     &lt;enumeration value="jo"/&gt;
 *     &lt;enumeration value="jp"/&gt;
 *     &lt;enumeration value="ke"/&gt;
 *     &lt;enumeration value="kg"/&gt;
 *     &lt;enumeration value="kh"/&gt;
 *     &lt;enumeration value="ki"/&gt;
 *     &lt;enumeration value="km"/&gt;
 *     &lt;enumeration value="kn"/&gt;
 *     &lt;enumeration value="kp"/&gt;
 *     &lt;enumeration value="kr"/&gt;
 *     &lt;enumeration value="kw"/&gt;
 *     &lt;enumeration value="ky"/&gt;
 *     &lt;enumeration value="kz"/&gt;
 *     &lt;enumeration value="la"/&gt;
 *     &lt;enumeration value="lb"/&gt;
 *     &lt;enumeration value="lc"/&gt;
 *     &lt;enumeration value="li"/&gt;
 *     &lt;enumeration value="lk"/&gt;
 *     &lt;enumeration value="lr"/&gt;
 *     &lt;enumeration value="ls"/&gt;
 *     &lt;enumeration value="lt"/&gt;
 *     &lt;enumeration value="lu"/&gt;
 *     &lt;enumeration value="lv"/&gt;
 *     &lt;enumeration value="ly"/&gt;
 *     &lt;enumeration value="ma"/&gt;
 *     &lt;enumeration value="mc"/&gt;
 *     &lt;enumeration value="md"/&gt;
 *     &lt;enumeration value="me"/&gt;
 *     &lt;enumeration value="mf"/&gt;
 *     &lt;enumeration value="mg"/&gt;
 *     &lt;enumeration value="mh"/&gt;
 *     &lt;enumeration value="mk"/&gt;
 *     &lt;enumeration value="ml"/&gt;
 *     &lt;enumeration value="mm"/&gt;
 *     &lt;enumeration value="mn"/&gt;
 *     &lt;enumeration value="mo"/&gt;
 *     &lt;enumeration value="mp"/&gt;
 *     &lt;enumeration value="mq"/&gt;
 *     &lt;enumeration value="mr"/&gt;
 *     &lt;enumeration value="ms"/&gt;
 *     &lt;enumeration value="mt"/&gt;
 *     &lt;enumeration value="mu"/&gt;
 *     &lt;enumeration value="mv"/&gt;
 *     &lt;enumeration value="mw"/&gt;
 *     &lt;enumeration value="mx"/&gt;
 *     &lt;enumeration value="my"/&gt;
 *     &lt;enumeration value="mz"/&gt;
 *     &lt;enumeration value="na"/&gt;
 *     &lt;enumeration value="nc"/&gt;
 *     &lt;enumeration value="ne"/&gt;
 *     &lt;enumeration value="nf"/&gt;
 *     &lt;enumeration value="ng"/&gt;
 *     &lt;enumeration value="ni"/&gt;
 *     &lt;enumeration value="nl"/&gt;
 *     &lt;enumeration value="no"/&gt;
 *     &lt;enumeration value="np"/&gt;
 *     &lt;enumeration value="nr"/&gt;
 *     &lt;enumeration value="nu"/&gt;
 *     &lt;enumeration value="nz"/&gt;
 *     &lt;enumeration value="om"/&gt;
 *     &lt;enumeration value="pa"/&gt;
 *     &lt;enumeration value="pe"/&gt;
 *     &lt;enumeration value="pf"/&gt;
 *     &lt;enumeration value="pg"/&gt;
 *     &lt;enumeration value="ph"/&gt;
 *     &lt;enumeration value="pk"/&gt;
 *     &lt;enumeration value="pl"/&gt;
 *     &lt;enumeration value="pm"/&gt;
 *     &lt;enumeration value="pn"/&gt;
 *     &lt;enumeration value="pr"/&gt;
 *     &lt;enumeration value="pt"/&gt;
 *     &lt;enumeration value="pw"/&gt;
 *     &lt;enumeration value="py"/&gt;
 *     &lt;enumeration value="qa"/&gt;
 *     &lt;enumeration value="re"/&gt;
 *     &lt;enumeration value="ro"/&gt;
 *     &lt;enumeration value="rs"/&gt;
 *     &lt;enumeration value="ru"/&gt;
 *     &lt;enumeration value="rw"/&gt;
 *     &lt;enumeration value="sa"/&gt;
 *     &lt;enumeration value="sb"/&gt;
 *     &lt;enumeration value="sc"/&gt;
 *     &lt;enumeration value="sd"/&gt;
 *     &lt;enumeration value="se"/&gt;
 *     &lt;enumeration value="sg"/&gt;
 *     &lt;enumeration value="sh"/&gt;
 *     &lt;enumeration value="si"/&gt;
 *     &lt;enumeration value="sj"/&gt;
 *     &lt;enumeration value="sk"/&gt;
 *     &lt;enumeration value="sl"/&gt;
 *     &lt;enumeration value="sm"/&gt;
 *     &lt;enumeration value="sn"/&gt;
 *     &lt;enumeration value="so"/&gt;
 *     &lt;enumeration value="sr"/&gt;
 *     &lt;enumeration value="ss"/&gt;
 *     &lt;enumeration value="st"/&gt;
 *     &lt;enumeration value="sv"/&gt;
 *     &lt;enumeration value="sx"/&gt;
 *     &lt;enumeration value="sy"/&gt;
 *     &lt;enumeration value="sz"/&gt;
 *     &lt;enumeration value="tc"/&gt;
 *     &lt;enumeration value="td"/&gt;
 *     &lt;enumeration value="tf"/&gt;
 *     &lt;enumeration value="tg"/&gt;
 *     &lt;enumeration value="th"/&gt;
 *     &lt;enumeration value="tj"/&gt;
 *     &lt;enumeration value="tk"/&gt;
 *     &lt;enumeration value="tl"/&gt;
 *     &lt;enumeration value="tm"/&gt;
 *     &lt;enumeration value="tn"/&gt;
 *     &lt;enumeration value="to"/&gt;
 *     &lt;enumeration value="tr"/&gt;
 *     &lt;enumeration value="tt"/&gt;
 *     &lt;enumeration value="tv"/&gt;
 *     &lt;enumeration value="tw"/&gt;
 *     &lt;enumeration value="tz"/&gt;
 *     &lt;enumeration value="ua"/&gt;
 *     &lt;enumeration value="ug"/&gt;
 *     &lt;enumeration value="uk"/&gt;
 *     &lt;enumeration value="um"/&gt;
 *     &lt;enumeration value="us"/&gt;
 *     &lt;enumeration value="uy"/&gt;
 *     &lt;enumeration value="uz"/&gt;
 *     &lt;enumeration value="va"/&gt;
 *     &lt;enumeration value="vc"/&gt;
 *     &lt;enumeration value="ve"/&gt;
 *     &lt;enumeration value="vg"/&gt;
 *     &lt;enumeration value="vi"/&gt;
 *     &lt;enumeration value="vn"/&gt;
 *     &lt;enumeration value="vu"/&gt;
 *     &lt;enumeration value="wf"/&gt;
 *     &lt;enumeration value="ws"/&gt;
 *     &lt;enumeration value="ye"/&gt;
 *     &lt;enumeration value="yt"/&gt;
 *     &lt;enumeration value="za"/&gt;
 *     &lt;enumeration value="zm"/&gt;
 *     &lt;enumeration value="zw"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "countryLiterals", namespace = "http://Inhaltsdaten.LegalDocML.de/1.6/")
@XmlEnum
public enum CountryLiterals {
  @XmlEnumValue("ad")
  AD("ad"),
  @XmlEnumValue("ae")
  AE("ae"),
  @XmlEnumValue("af")
  AF("af"),
  @XmlEnumValue("ag")
  AG("ag"),
  @XmlEnumValue("ai")
  AI("ai"),
  @XmlEnumValue("al")
  AL("al"),
  @XmlEnumValue("am")
  AM("am"),
  @XmlEnumValue("ao")
  AO("ao"),
  @XmlEnumValue("aq")
  AQ("aq"),
  @XmlEnumValue("ar")
  AR("ar"),
  @XmlEnumValue("as")
  AS("as"),
  @XmlEnumValue("at")
  AT("at"),
  @XmlEnumValue("au")
  AU("au"),
  @XmlEnumValue("aw")
  AW("aw"),
  @XmlEnumValue("ax")
  AX("ax"),
  @XmlEnumValue("az")
  AZ("az"),
  @XmlEnumValue("ba")
  BA("ba"),
  @XmlEnumValue("bb")
  BB("bb"),
  @XmlEnumValue("bd")
  BD("bd"),
  @XmlEnumValue("be")
  BE("be"),
  @XmlEnumValue("bf")
  BF("bf"),
  @XmlEnumValue("bg")
  BG("bg"),
  @XmlEnumValue("bh")
  BH("bh"),
  @XmlEnumValue("bi")
  BI("bi"),
  @XmlEnumValue("bj")
  BJ("bj"),
  @XmlEnumValue("bl")
  BL("bl"),
  @XmlEnumValue("bm")
  BM("bm"),
  @XmlEnumValue("bn")
  BN("bn"),
  @XmlEnumValue("bo")
  BO("bo"),
  @XmlEnumValue("br")
  BR("br"),
  @XmlEnumValue("bs")
  BS("bs"),
  @XmlEnumValue("bt")
  BT("bt"),
  @XmlEnumValue("bv")
  BV("bv"),
  @XmlEnumValue("bw")
  BW("bw"),
  @XmlEnumValue("by")
  BY("by"),
  @XmlEnumValue("bz")
  BZ("bz"),
  @XmlEnumValue("ca")
  CA("ca"),
  @XmlEnumValue("cc")
  CC("cc"),
  @XmlEnumValue("cd")
  CD("cd"),
  @XmlEnumValue("cf")
  CF("cf"),
  @XmlEnumValue("cg")
  CG("cg"),
  @XmlEnumValue("ch")
  CH("ch"),
  @XmlEnumValue("ci")
  CI("ci"),
  @XmlEnumValue("ck")
  CK("ck"),
  @XmlEnumValue("cl")
  CL("cl"),
  @XmlEnumValue("cm")
  CM("cm"),
  @XmlEnumValue("cn")
  CN("cn"),
  @XmlEnumValue("co")
  CO("co"),
  @XmlEnumValue("cp")
  CP("cp"),
  @XmlEnumValue("cr")
  CR("cr"),
  @XmlEnumValue("cu")
  CU("cu"),
  @XmlEnumValue("cv")
  CV("cv"),
  @XmlEnumValue("cw")
  CW("cw"),
  @XmlEnumValue("cx")
  CX("cx"),
  @XmlEnumValue("cy")
  CY("cy"),
  @XmlEnumValue("cz")
  CZ("cz"),
  @XmlEnumValue("de")
  DE("de"),
  @XmlEnumValue("dj")
  DJ("dj"),
  @XmlEnumValue("dk")
  DK("dk"),
  @XmlEnumValue("dm")
  DM("dm"),
  @XmlEnumValue("do")
  DO("do"),
  @XmlEnumValue("dz")
  DZ("dz"),
  @XmlEnumValue("ec")
  EC("ec"),
  @XmlEnumValue("ee")
  EE("ee"),
  @XmlEnumValue("eg")
  EG("eg"),
  @XmlEnumValue("eh")
  EH("eh"),
  @XmlEnumValue("el")
  EL("el"),
  @XmlEnumValue("er")
  ER("er"),
  @XmlEnumValue("es")
  ES("es"),
  @XmlEnumValue("et")
  ET("et"),
  @XmlEnumValue("fi")
  FI("fi"),
  @XmlEnumValue("fj")
  FJ("fj"),
  @XmlEnumValue("fk")
  FK("fk"),
  @XmlEnumValue("fm")
  FM("fm"),
  @XmlEnumValue("fo")
  FO("fo"),
  @XmlEnumValue("fr")
  FR("fr"),
  @XmlEnumValue("ga")
  GA("ga"),
  @XmlEnumValue("gd")
  GD("gd"),
  @XmlEnumValue("ge")
  GE("ge"),
  @XmlEnumValue("gf")
  GF("gf"),
  @XmlEnumValue("gg")
  GG("gg"),
  @XmlEnumValue("gh")
  GH("gh"),
  @XmlEnumValue("gi")
  GI("gi"),
  @XmlEnumValue("gl")
  GL("gl"),
  @XmlEnumValue("gm")
  GM("gm"),
  @XmlEnumValue("gn")
  GN("gn"),
  @XmlEnumValue("gp")
  GP("gp"),
  @XmlEnumValue("gq")
  GQ("gq"),
  @XmlEnumValue("gs")
  GS("gs"),
  @XmlEnumValue("gt")
  GT("gt"),
  @XmlEnumValue("gu")
  GU("gu"),
  @XmlEnumValue("gw")
  GW("gw"),
  @XmlEnumValue("gy")
  GY("gy"),
  @XmlEnumValue("hk")
  HK("hk"),
  @XmlEnumValue("hm")
  HM("hm"),
  @XmlEnumValue("hn")
  HN("hn"),
  @XmlEnumValue("hr")
  HR("hr"),
  @XmlEnumValue("ht")
  HT("ht"),
  @XmlEnumValue("hu")
  HU("hu"),
  @XmlEnumValue("id")
  ID("id"),
  @XmlEnumValue("ie")
  IE("ie"),
  @XmlEnumValue("il")
  IL("il"),
  @XmlEnumValue("im")
  IM("im"),
  @XmlEnumValue("in")
  IN("in"),
  @XmlEnumValue("io")
  IO("io"),
  @XmlEnumValue("iq")
  IQ("iq"),
  @XmlEnumValue("ir")
  IR("ir"),
  @XmlEnumValue("is")
  IS("is"),
  @XmlEnumValue("it")
  IT("it"),
  @XmlEnumValue("je")
  JE("je"),
  @XmlEnumValue("jm")
  JM("jm"),
  @XmlEnumValue("jo")
  JO("jo"),
  @XmlEnumValue("jp")
  JP("jp"),
  @XmlEnumValue("ke")
  KE("ke"),
  @XmlEnumValue("kg")
  KG("kg"),
  @XmlEnumValue("kh")
  KH("kh"),
  @XmlEnumValue("ki")
  KI("ki"),
  @XmlEnumValue("km")
  KM("km"),
  @XmlEnumValue("kn")
  KN("kn"),
  @XmlEnumValue("kp")
  KP("kp"),
  @XmlEnumValue("kr")
  KR("kr"),
  @XmlEnumValue("kw")
  KW("kw"),
  @XmlEnumValue("ky")
  KY("ky"),
  @XmlEnumValue("kz")
  KZ("kz"),
  @XmlEnumValue("la")
  LA("la"),
  @XmlEnumValue("lb")
  LB("lb"),
  @XmlEnumValue("lc")
  LC("lc"),
  @XmlEnumValue("li")
  LI("li"),
  @XmlEnumValue("lk")
  LK("lk"),
  @XmlEnumValue("lr")
  LR("lr"),
  @XmlEnumValue("ls")
  LS("ls"),
  @XmlEnumValue("lt")
  LT("lt"),
  @XmlEnumValue("lu")
  LU("lu"),
  @XmlEnumValue("lv")
  LV("lv"),
  @XmlEnumValue("ly")
  LY("ly"),
  @XmlEnumValue("ma")
  MA("ma"),
  @XmlEnumValue("mc")
  MC("mc"),
  @XmlEnumValue("md")
  MD("md"),
  @XmlEnumValue("me")
  ME("me"),
  @XmlEnumValue("mf")
  MF("mf"),
  @XmlEnumValue("mg")
  MG("mg"),
  @XmlEnumValue("mh")
  MH("mh"),
  @XmlEnumValue("mk")
  MK("mk"),
  @XmlEnumValue("ml")
  ML("ml"),
  @XmlEnumValue("mm")
  MM("mm"),
  @XmlEnumValue("mn")
  MN("mn"),
  @XmlEnumValue("mo")
  MO("mo"),
  @XmlEnumValue("mp")
  MP("mp"),
  @XmlEnumValue("mq")
  MQ("mq"),
  @XmlEnumValue("mr")
  MR("mr"),
  @XmlEnumValue("ms")
  MS("ms"),
  @XmlEnumValue("mt")
  MT("mt"),
  @XmlEnumValue("mu")
  MU("mu"),
  @XmlEnumValue("mv")
  MV("mv"),
  @XmlEnumValue("mw")
  MW("mw"),
  @XmlEnumValue("mx")
  MX("mx"),
  @XmlEnumValue("my")
  MY("my"),
  @XmlEnumValue("mz")
  MZ("mz"),
  @XmlEnumValue("na")
  NA("na"),
  @XmlEnumValue("nc")
  NC("nc"),
  @XmlEnumValue("ne")
  NE("ne"),
  @XmlEnumValue("nf")
  NF("nf"),
  @XmlEnumValue("ng")
  NG("ng"),
  @XmlEnumValue("ni")
  NI("ni"),
  @XmlEnumValue("nl")
  NL("nl"),
  @XmlEnumValue("no")
  NO("no"),
  @XmlEnumValue("np")
  NP("np"),
  @XmlEnumValue("nr")
  NR("nr"),
  @XmlEnumValue("nu")
  NU("nu"),
  @XmlEnumValue("nz")
  NZ("nz"),
  @XmlEnumValue("om")
  OM("om"),
  @XmlEnumValue("pa")
  PA("pa"),
  @XmlEnumValue("pe")
  PE("pe"),
  @XmlEnumValue("pf")
  PF("pf"),
  @XmlEnumValue("pg")
  PG("pg"),
  @XmlEnumValue("ph")
  PH("ph"),
  @XmlEnumValue("pk")
  PK("pk"),
  @XmlEnumValue("pl")
  PL("pl"),
  @XmlEnumValue("pm")
  PM("pm"),
  @XmlEnumValue("pn")
  PN("pn"),
  @XmlEnumValue("pr")
  PR("pr"),
  @XmlEnumValue("pt")
  PT("pt"),
  @XmlEnumValue("pw")
  PW("pw"),
  @XmlEnumValue("py")
  PY("py"),
  @XmlEnumValue("qa")
  QA("qa"),
  @XmlEnumValue("re")
  RE("re"),
  @XmlEnumValue("ro")
  RO("ro"),
  @XmlEnumValue("rs")
  RS("rs"),
  @XmlEnumValue("ru")
  RU("ru"),
  @XmlEnumValue("rw")
  RW("rw"),
  @XmlEnumValue("sa")
  SA("sa"),
  @XmlEnumValue("sb")
  SB("sb"),
  @XmlEnumValue("sc")
  SC("sc"),
  @XmlEnumValue("sd")
  SD("sd"),
  @XmlEnumValue("se")
  SE("se"),
  @XmlEnumValue("sg")
  SG("sg"),
  @XmlEnumValue("sh")
  SH("sh"),
  @XmlEnumValue("si")
  SI("si"),
  @XmlEnumValue("sj")
  SJ("sj"),
  @XmlEnumValue("sk")
  SK("sk"),
  @XmlEnumValue("sl")
  SL("sl"),
  @XmlEnumValue("sm")
  SM("sm"),
  @XmlEnumValue("sn")
  SN("sn"),
  @XmlEnumValue("so")
  SO("so"),
  @XmlEnumValue("sr")
  SR("sr"),
  @XmlEnumValue("ss")
  SS("ss"),
  @XmlEnumValue("st")
  ST("st"),
  @XmlEnumValue("sv")
  SV("sv"),
  @XmlEnumValue("sx")
  SX("sx"),
  @XmlEnumValue("sy")
  SY("sy"),
  @XmlEnumValue("sz")
  SZ("sz"),
  @XmlEnumValue("tc")
  TC("tc"),
  @XmlEnumValue("td")
  TD("td"),
  @XmlEnumValue("tf")
  TF("tf"),
  @XmlEnumValue("tg")
  TG("tg"),
  @XmlEnumValue("th")
  TH("th"),
  @XmlEnumValue("tj")
  TJ("tj"),
  @XmlEnumValue("tk")
  TK("tk"),
  @XmlEnumValue("tl")
  TL("tl"),
  @XmlEnumValue("tm")
  TM("tm"),
  @XmlEnumValue("tn")
  TN("tn"),
  @XmlEnumValue("to")
  TO("to"),
  @XmlEnumValue("tr")
  TR("tr"),
  @XmlEnumValue("tt")
  TT("tt"),
  @XmlEnumValue("tv")
  TV("tv"),
  @XmlEnumValue("tw")
  TW("tw"),
  @XmlEnumValue("tz")
  TZ("tz"),
  @XmlEnumValue("ua")
  UA("ua"),
  @XmlEnumValue("ug")
  UG("ug"),
  @XmlEnumValue("uk")
  UK("uk"),
  @XmlEnumValue("um")
  UM("um"),
  @XmlEnumValue("us")
  US("us"),
  @XmlEnumValue("uy")
  UY("uy"),
  @XmlEnumValue("uz")
  UZ("uz"),
  @XmlEnumValue("va")
  VA("va"),
  @XmlEnumValue("vc")
  VC("vc"),
  @XmlEnumValue("ve")
  VE("ve"),
  @XmlEnumValue("vg")
  VG("vg"),
  @XmlEnumValue("vi")
  VI("vi"),
  @XmlEnumValue("vn")
  VN("vn"),
  @XmlEnumValue("vu")
  VU("vu"),
  @XmlEnumValue("wf")
  WF("wf"),
  @XmlEnumValue("ws")
  WS("ws"),
  @XmlEnumValue("ye")
  YE("ye"),
  @XmlEnumValue("yt")
  YT("yt"),
  @XmlEnumValue("za")
  ZA("za"),
  @XmlEnumValue("zm")
  ZM("zm"),
  @XmlEnumValue("zw")
  ZW("zw");
  private final String value;

  CountryLiterals(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static CountryLiterals fromValue(String v) {
    for (CountryLiterals c : CountryLiterals.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
