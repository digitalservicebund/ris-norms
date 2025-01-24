-- REAPPLY
-- TARGET LAW
DELETE
FROM dokumente
WHERE eli_dokument_expression = 'eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-1';

INSERT INTO dokumente (publish_state, xml)
VALUES ('PUBLISHED', '<akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten.xsd http://MetadatenBundesregierung.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-metadaten-bundesregierung.xsd http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
  <akn:act name="regelungstext">
    <akn:meta GUID="95331d84-69b2-4f2d-a20a-d9bd802cfecc" eId="meta-1">
      <akn:identification source="attributsemantik-noch-undefiniert" GUID="71fd03aa-f8ed-4928-90bf-1e48546bc8d1" eId="meta-1_ident-1">
        <akn:FRBRWork GUID="f6bc23d4-049a-41a7-9a5f-070915fe7bca" eId="meta-1_ident-1_frbrwork-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1000/s1/regelungstext-1" GUID="6fca0a13-efd2-4a17-81fa-c519bbb5f62a" eId="meta-1_ident-1_frbrwork-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1000/s1" GUID="a4de83e8-8dd2-4d88-a4f8-dc46caf9061e" eId="meta-1_ident-1_frbrwork-1_frbruri-1"/>
          <akn:FRBRalias name="übergreifende-id" value="fb457ea6-87ad-43ce-8234-ceb4a0cb1bc6" GUID="7b45a6c3-8041-41a7-bd50-ff4e00c16a56" eId="meta-1_ident-1_frbrwork-1_frbralias-1"/>
          <akn:FRBRdate name="verkuendungsfassung" date="1000-01-01" GUID="02d1d682-3b75-470a-9a5e-23edf99f0211" eId="meta-1_ident-1_frbrwork-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="7ed646cc-fd2c-4013-958e-b867d430c368" eId="meta-1_ident-1_frbrwork-1_frbrauthor-1"/>
          <akn:FRBRcountry value="de" GUID="8642c92f-bb6b-45c2-bc81-8f634a12b722" eId="meta-1_ident-1_frbrwork-1_frbrcountry-1"/>
          <akn:FRBRnumber value="s1" GUID="dd3682ef-55f0-4932-be41-bd3692c9b071" eId="meta-1_ident-1_frbrwork-1_frbrnumber-1"/>
          <akn:FRBRname value="bgbl-1" GUID="89d40329-8cf9-45f2-91a6-50a001df43bd" eId="meta-1_ident-1_frbrwork-1_frbrname-1"/>
          <akn:FRBRsubtype value="regelungstext-1" GUID="277cbef6-f1cd-45c0-b717-704159cf2ec0" eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1"/>
        </akn:FRBRWork>
        <akn:FRBRExpression GUID="1d435cd3-2351-46ae-b46c-dc2ef0568535" eId="meta-1_ident-1_frbrexpression-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-1" GUID="60c538a1-1ba4-4bf0-b95c-79c613bff8c6" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu" GUID="f42f3441-3f8c-4266-b351-14776ae1c8c3" eId="meta-1_ident-1_frbrexpression-1_frbruri-1"/>
          <akn:FRBRalias name="aktuelle-version-id" value="d33c67a0-2be2-4728-932d-5abae5a84422" GUID="865d0a63-371b-4a0e-8354-04fa6de20a3b" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"/>
          <akn:FRBRalias name="nachfolgende-version-id" value="24c51028-eb62-4853-a986-6c62e6e25731" GUID="bb2927b6-dfd4-45f4-a1b1-46d2fdc4cd2a" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"/>
          <akn:FRBRauthor href="recht.bund.de/institution/bundesregierung" GUID="c39d82d9-bb0b-4a36-bf4f-9f7d44787375" eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1"/>
          <akn:FRBRdate name="verkuendung" date="1000-01-01" GUID="32f960ab-5326-4885-b41b-692ba3664bf1" eId="meta-1_ident-1_frbrexpression-1_frbrdate-1"/>
          <akn:FRBRlanguage language="deu" GUID="a86cc62f-6ba7-471b-82b8-fcefaad5a274" eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1"/>
          <akn:FRBRversionNumber value="1" GUID="9d5c5bde-63dc-4696-b0c3-2ac88cde2b7e" eId="meta-1_ident-1_frbrexpression-1_frbrversionnumber-1"/>
        </akn:FRBRExpression>
        <akn:FRBRManifestation GUID="60e359ee-945e-4392-9f24-786e840aaf40" eId="meta-1_ident-1_frbrmanifestation-1">
          <akn:FRBRthis value="eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/1000-01-01/regelungstext-1.xml" GUID="2eaa2e38-784f-4372-8f4b-d6cfcc7c1fdb" eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"/>
          <akn:FRBRuri value="eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/1000-01-01/regelungstext-1.xml" GUID="8b4b1d94-db22-4ce4-92d1-93468e6ad039" eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"/>
          <akn:FRBRdate name="generierung" date="1000-01-01" GUID="5e54e633-ef95-45cb-8f6f-e948ce26168d" eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"/>
          <akn:FRBRauthor href="recht.bund.de" GUID="487676ec-d3b3-49f9-8142-92632628aad6" eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"/>
          <akn:FRBRformat value="xml" GUID="17f679e9-160b-43da-a93b-d736fc806a33" eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"/>
        </akn:FRBRManifestation>
      </akn:identification>
      <akn:lifecycle source="attributsemantik-noch-undefiniert" GUID="6cdf4a51-2151-4204-b9e4-26e508f46dd7" eId="meta-1_lebzykl-1">
        <akn:eventRef date="1000-01-01" refersTo="ausfertigung" type="generation" source="attributsemantik-noch-undefiniert" GUID="06bcf344-ac14-4a2d-9097-690a441c4f76" eId="meta-1_lebzykl-1_ereignis-1"/>
        <akn:eventRef date="1000-01-01" refersTo="inkrafttreten" type="generation" source="attributsemantik-noch-undefiniert" GUID="e12ded4a-d630-4a79-ad37-ea1e4a3a5c24" eId="meta-1_lebzykl-1_ereignis-2"/>
      </akn:lifecycle>
      <akn:analysis source="attributsemantik-noch-undefiniert" GUID="eb6acc40-9e71-4bda-8bc5-9cdd07059308" eId="meta-1_analysis-1">
        <akn:activeModifications GUID="f3ea759f-d331-443d-a82b-e5f98612d62f" eId="meta-1_analysis-1_activemod-1">
          <akn:textualMod type="substitution" GUID="34e7c3ca-9a92-434c-9ec6-b355738f9d30" eId="meta-1_analysis-1_activemod-1_textualmod-1">
            <akn:source href="#hauptteil-1_art-4_abs-2_inhalt-1_text-1_ändbefehl-1" GUID="7b2d228d-426a-45b1-a8ef-7bb10759c865" eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"/>
            <akn:destination href="#" GUID="d10ed225-81c6-4cef-8a0a-34d42277f5f8" eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"/>
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-1" GUID="613f7cf0-cd6c-49ab-bc8f-e3032775c0ad" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"/>
          </akn:textualMod>
          <akn:textualMod type="substitution" GUID="b741b469-ed7e-46fe-8bff-a632ae31f63b" eId="meta-1_analysis-1_activemod-1_textualmod-2">
            <akn:source href="#hauptteil-1_art-4_abs-3_inhalt-1_text-1_ändbefehl-1" GUID="019b01f6-b845-4d5d-b659-7f629d0e9856" eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1"/>
            <akn:destination href="eli-noch-undefiniert" GUID="48810bf3-30ee-4c07-af46-fa67e8765480" eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1"/>
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-1" GUID="36359f74-abf9-4ae0-8164-cbd18716f7cb" eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1"/>
          </akn:textualMod>
          <akn:textualMod type="repeal" GUID="dff977a9-6810-4004-80a2-24b8d7731b91" eId="meta-1_analysis-1_activemod-1_textualmod-3">
            <akn:source href="#hauptteil-1_art-4_abs-4_inhalt-1_text-1_ändbefehl-1" GUID="7efd86fa-2627-494b-8a61-fe3c24755b0e" eId="meta-1_analysis-1_activemod-1_textualmod-3_source-1"/>
            <akn:destination href="eli-noch-undefiniert" GUID="a71fc44d-9c45-4340-b424-e03c725dff47" eId="meta-1_analysis-1_activemod-1_textualmod-3_destination-1"/>
            <akn:force period="#meta-1_geltzeiten-1_geltungszeitgr-1" GUID="2c6464ce-df30-4b6b-96fc-98f1239c75f6" eId="meta-1_analysis-1_activemod-1_textualmod-3_gelzeitnachw-1"/>
          </akn:textualMod>
        </akn:activeModifications>
      </akn:analysis>
      <akn:temporalData source="attributsemantik-noch-undefiniert" GUID="53a94f33-1bb9-4fb7-8882-baa3178c1370" eId="meta-1_geltzeiten-1">
        <akn:temporalGroup GUID="6262819c-9391-407a-9225-497f3232d283" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
          <akn:timeInterval refersTo="geltungszeit" GUID="d19a31d1-d710-40b6-b1c1-4507e4e9e54c" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" start="#meta-1_lebzykl-1_ereignis-1"/>
        </akn:temporalGroup>
      </akn:temporalData>
    </akn:meta>
    <akn:preface GUID="65e7dbea-2fc2-4aff-ae62-e4f3e9ebfe1e" eId="einleitung-1">
      <akn:longTitle GUID="f12a0e92-21f4-4581-84a8-1622f3d06131" eId="einleitung-1_doktitel-1">
        <akn:p GUID="61ead6d0-94ba-4a0b-98df-eabb2e51d85d" eId="einleitung-1_doktitel-1_text-1">
          <akn:docTitle GUID="f3fe87d5-bf0a-4d3b-ac2c-152f6fab059a" eId="einleitung-1_doktitel-1_text-1_doctitel-1">Mock Formatting Test</akn:docTitle>
        </akn:p>
      </akn:longTitle>
    </akn:preface>
    <akn:body GUID="7841f98b-118f-4e43-81e1-518d996769f9" eId="hauptteil-1">
      <akn:article GUID="625630db-6d2b-4832-bece-5ddfebb47c47" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="335cf281-5363-491a-b15c-eee983e1c2fd" eId="hauptteil-1_art-1_bezeichnung-1"/>
        <akn:heading GUID="4a243dbf-fd25-4ca1-b3ee-6ca3cfe56db9" eId="hauptteil-1_art-1_überschrift-1">Basic HTML Elements</akn:heading>
        <akn:paragraph GUID="440a9563-7173-45f7-8841-85bd00e61afd" eId="hauptteil-1_art-1_abs-1">
          <akn:num GUID="006095b2-7766-43ed-bb8f-d5ac06eaf363" eId="hauptteil-1_art-1_abs-1_bezeichnung-1"/>
          <akn:content GUID="b5a21e82-cbcd-4a53-ab28-3f48b4c40c15" eId="hauptteil-1_art-1_abs-1_inhalt-1">
            <akn:p GUID="cf6fc00e-b9da-41b6-b027-f1f0bf49d673" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1">
              <akn:b GUID="ca1a27c7-8d92-4339-ae0c-70fb09fc1920" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_fettschrift-1">Bold</akn:b>
              text.
              <akn:i GUID="95fa4600-0749-4ba8-adf7-632e1590b3d9" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_kursiv-1">Italic</akn:i>
              text.
              <akn:u GUID="0c0b3742-338c-481a-b793-ad46b7f05afb" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_u-1">Underlined</akn:u>
              text. This contains
              <akn:sub GUID="9128ed3f-d7af-45e8-9595-0c0f76f0c295" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_sub-1">subscript</akn:sub>
              text. This contains
              <akn:sup GUID="097fdcd2-bc2a-4b2c-a1a7-85809a44361e" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_sup-1">superscript</akn:sup>
              text. This text contains a
              <akn:a href="#" GUID="92b8f377-b82d-4449-90f6-aea361fd4f61" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_a-1">link</akn:a>
              .
              <akn:br GUID="d2a805fd-89c5-4ac0-beb0-d608a48145de" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_br-1"/>
              <akn:br GUID="e3cb4a49-5272-4296-9fd2-4c445d2da0b6" eId="hauptteil-1_art-1_abs-1_inhalt-1_text-1_br-2"/>
              This text has two preceding line breaks.
            </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="f52b7366-7e99-4c15-b408-563606c2f329" eId="hauptteil-1_art-2" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="50f68296-b627-4024-9df0-c984325535b0" eId="hauptteil-1_art-2_bezeichnung-1"/>
        <akn:heading GUID="f5527f96-6190-4a75-9b15-7143c8063412" eId="hauptteil-1_art-2_überschrift-1"> Lists </akn:heading>
        <akn:paragraph GUID="95a96e0c-bb6c-4898-8dd3-2721054cc2cd" eId="hauptteil-1_art-2_abs-1">
          <akn:num GUID="4343d003-ade6-4259-87a8-3eb7c41597a9" eId="hauptteil-1_art-2_abs-1_bezeichnung-1"/>
          <akn:list GUID="b936694e-4680-4763-b59f-7a6f3d890353" eId="hauptteil-1_art-2_abs-1_untergl-1">
            <akn:intro GUID="2cef57c3-6b38-4561-abf3-e875bf43dfdd" eId="hauptteil-1_art-2_abs-1_untergl-1_intro-1">
              <akn:p GUID="dbbbecdc-374d-457b-b749-997147574e52" eId="hauptteil-1_art-2_abs-1_untergl-1_intro-1_text-1">This is the intro to the following list:</akn:p>
            </akn:intro>
            <akn:point GUID="73220071-f6f3-46f1-9470-6f74d987071b" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1">
              <akn:num GUID="ef9f6ce6-4412-42ce-b290-15dd650ab4aa" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_bezeichnung-1"/>
              <akn:list GUID="29d1617f-5a1a-4dfe-af05-08e49130649d" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1">
                <akn:intro GUID="9c0854ba-77cd-4cfe-8209-3ccc4a2ba5a9" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_intro-1">
                  <akn:p GUID="00c33129-49ac-4040-b871-1e893de8293a" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_intro-1_text-1">This is the first item</akn:p>
                </akn:intro>
                <akn:point GUID="044e4590-14ae-46ea-8f23-f4999845b5f4" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1">
                  <akn:num GUID="bd436b4f-c99c-4575-aef5-3ee595703dca" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_bezeichnung-1"/>
                  <akn:content GUID="5f0253d7-55ac-491d-82f4-15df2d089ef3" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1">
                    <akn:p GUID="3c5a9e6d-d62d-4a8f-be50-2e5c825181d0" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-1_inhalt-1_text-1">This is the first sub item.</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="1092eabf-ab54-4a1b-b11c-eedba53835c3" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2">
                  <akn:num GUID="276ed014-1350-4595-a70a-003c25735f1e" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_bezeichnung-1"/>
                  <akn:content GUID="df2a2164-eb2e-421c-8740-b9d67174c377" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1">
                    <akn:p GUID="0c9b38a0-6481-41f5-ba5a-cd4dafbbe55c" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-2_inhalt-1_text-1">This is the second sub item.</akn:p>
                  </akn:content>
                </akn:point>
                <akn:point GUID="3ac355ad-96dc-44e4-a7c1-6e986c666c3a" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3">
                  <akn:num GUID="9c751fd1-39d7-4011-830a-7f3fc151f22e" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3_bezeichnung-1"/>
                  <akn:content GUID="d992de84-d503-43f7-86be-228829ecf95f" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1">
                    <akn:p GUID="303e4d60-d3f2-49c3-a25b-4c9b170490da" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-1_untergl-1_listenelem-3_inhalt-1_text-1">This is the third sub item.</akn:p>
                  </akn:content>
                </akn:point>
              </akn:list>
            </akn:point>
            <akn:point GUID="f0296e94-f871-488d-a02a-40071876198c" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2">
              <akn:num GUID="52156187-2226-4363-a0bd-a3b912d8e7fa" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_bezeichnung-1"/>
              <akn:content GUID="3f8e14eb-c1e0-457c-9eed-8d36839a87ce" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_inhalt-1">
                <akn:p GUID="2ff5b711-fbd6-4d8a-a44f-535b802eaf8d" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">This is the second list item</akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="cedd5012-5ddd-40aa-999e-5db9ff654d52" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3">
              <akn:num GUID="86502073-95bb-4294-bc07-7badbf9c5670" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_bezeichnung-1"/>
              <akn:content GUID="64b16cbc-8778-4838-8239-b8ec71373d71" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_inhalt-1">
                <akn:p GUID="22aa60c6-40d9-43a5-ba40-07e84e6966b6" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-3_inhalt-1_text-1">This is the third list item </akn:p>
              </akn:content>
            </akn:point>
            <akn:point GUID="52dfdfbb-0810-4717-bc9d-345932c92987" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4">
              <akn:num GUID="8a2566d2-6de6-4658-85a7-85ebe88551ce" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_bezeichnung-1"/>
              <akn:content GUID="1a6fccf5-bf2e-4550-97ed-b3af6ee79aa6" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_inhalt-1">
                <akn:p GUID="e9fd6b62-82b9-4cc4-9a6e-1b6b76957d5b" eId="hauptteil-1_art-2_abs-1_untergl-1_listenelem-4_inhalt-1_text-1">This is the fourth list item.</akn:p>
              </akn:content>
            </akn:point>
            <akn:wrapUp GUID="1d234bf0-b4e1-46bf-be69-8c5347425c59" eId="hauptteil-1_art-2_abs-1_untergl-1_schlusstext-1">
              <akn:p GUID="49f9c479-d700-49aa-ba1d-496fcb8cdd75" eId="hauptteil-1_art-2_abs-1_untergl-1_schlusstext-1_text-1">This is the outro of the list.</akn:p>
            </akn:wrapUp>
          </akn:list>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="4c978896-bb89-4e95-8fe0-cc338f56c293" eId="hauptteil-1_art-3" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="496c869d-b0fc-4a40-ab0d-6711c12dcf9e" eId="hauptteil-1_art-3_bezeichnung-1"/>
        <akn:heading GUID="7c3bf734-7d72-40b0-ace2-29c47fa14c11" eId="hauptteil-1_art-3_überschrift-1"> Tables </akn:heading>
        <akn:paragraph GUID="0bbac1b2-b1ac-4e14-90c2-b7aa33fee553" eId="hauptteil-1_art-3_abs-1">
          <akn:num GUID="2f5d0efc-a9a0-4574-a962-2a4e4759257a" eId="hauptteil-1_art-3_abs-1_bezeichnung-1"/>
          <akn:content GUID="5274353a-59a0-435c-94b9-766fc0c2a386" eId="hauptteil-1_art-3_abs-1_inhalt-1">
            <akn:p GUID="4a30d98b-39f7-4876-83c9-b3f5dcd6280b" eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1"> An ordered list: </akn:p>
            <akn:table border="1" GUID="b4cbe1dd-6f98-4530-9a47-8c21479fa13d" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1">
              <akn:tr GUID="9b5b75f0-7fb7-4657-8f1a-506290187923" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1">
                <akn:th GUID="dac86433-d4c9-45d0-8684-2d227a70aa43" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1">
                  <akn:p GUID="65a24a62-90c2-4581-b408-c1183c3eab00" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-1_text-1">Table Header, Cell 1</akn:p>
                </akn:th>
                <akn:th GUID="3dfb05db-07b0-4118-8c8c-ce3841b17675" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2">
                  <akn:p GUID="f9662008-1c3f-4a9c-8dbd-a8e1a84fc2c9" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-2_text-1">Table Header, Cell 2</akn:p>
                </akn:th>
                <akn:th GUID="819c4f17-5e57-4524-b52f-a17858e66b47" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-3">
                  <akn:p GUID="52cabfcc-6a91-43ef-9a6e-d781b376a229" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-1_tabellekopf-3_text-1">Table Header, Cell 3</akn:p>
                </akn:th>
              </akn:tr>
              <akn:tr GUID="c4e92d21-26af-4c71-82c5-b978a60ea71b" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2">
                <akn:td GUID="1a60e0c7-1d9b-41fc-b840-a0979aa614f8" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1">
                  <akn:p GUID="a8ac19df-6534-40ca-8fdf-55a808a7904a" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-1_text-1">Row 1, Cell 1</akn:p>
                </akn:td>
                <akn:td GUID="8a360568-11c9-461d-960c-6f939d325904" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2">
                  <akn:p GUID="b8c014ad-7317-410c-82e2-5a1c1d52145b" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-2_text-1">Row 1, Cell 2</akn:p>
                </akn:td>
                <akn:td GUID="21c36353-823d-485b-9fa5-20393b087d59" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-3">
                  <akn:p GUID="42fb936a-fd38-4511-bde6-3fdd4b748cde" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-2_tabelleinh-3_text-1">Row 1, Cell 3</akn:p>
                </akn:td>
              </akn:tr>
              <akn:tr GUID="d8a9c3d5-ee63-4a60-8bf8-7375633d6c1b" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3">
                <akn:td GUID="0323d2e5-3951-4dc4-bda9-98d87e7b6c37" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1">
                  <akn:p GUID="13f7b84b-fd75-4a42-a83e-e851ceee9b93" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-1_text-1">Row 2, Cell 1</akn:p>
                </akn:td>
                <akn:td GUID="a7563561-31d7-4974-9016-fcc115cc6e45" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2">
                  <akn:p GUID="424e9227-f160-4fb2-b74f-2acd7a6e2948" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-2_text-1">Row 2, Cell 2</akn:p>
                </akn:td>
                <akn:td GUID="59ee2124-928d-49c7-8a60-d47f68f9c7cc" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-3">
                  <akn:p GUID="f88d29e7-cd5c-4e8a-9e9c-c1c0c173d852" eId="hauptteil-1_art-3_abs-1_inhalt-1_tabelle-1_tabellereihe-3_tabelleinh-3_text-1">Row 2, Cell 3</akn:p>
                </akn:td>
              </akn:tr>
            </akn:table>
          </akn:content>
        </akn:paragraph>
      </akn:article>
      <akn:article GUID="a2d6af44-89f3-4b52-aee4-1631f629b6e2" eId="hauptteil-1_art-4" period="#meta-1_geltzeiten-1_geltungszeitgr-1">
        <akn:num GUID="8d2d56f8-ab9e-4d24-9955-9af4474dce38" eId="hauptteil-1_art-4_bezeichnung-1"/>
        <akn:heading GUID="5dea9cdc-c518-4e8d-9257-8823390a95a9" eId="hauptteil-1_art-4_überschrift-1">Modifications</akn:heading>
        <akn:paragraph GUID="57e9bb8b-61ed-40fa-97ed-2916a18b91aa" eId="hauptteil-1_art-4_abs-1">
          <akn:num GUID="1b4a63fa-0920-4669-84c3-96173ca4a391" eId="hauptteil-1_art-4_abs-1_bezeichnung-1"/>
          <akn:content GUID="023436db-0568-4462-b24f-b534b9ccdba2" eId="hauptteil-1_art-4_abs-1_inhalt-1">
            <akn:p GUID="c113feaf-be68-41ae-9ee8-562b73a54f91" eId="hauptteil-1_art-4_abs-1_inhalt-1_text-1"> This paragraph references an <akn:affectedDocument href="eli-noch-undefiniert" GUID="ba4c9479-27b3-4129-b1c4-60d5b010ee3a" eId="hauptteil-1_art-4_abs-1_inhalt-1_text-1_bezugsdoc-1">affected document</akn:affectedDocument>.</akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="869612fe-3f16-4bb4-9366-959e2d0a6259" eId="hauptteil-1_art-4_abs-2">
          <akn:num GUID="7d1beb9d-4086-438d-971a-36ac2344cc3f" eId="hauptteil-1_art-4_abs-2_bezeichnung-1"/>
          <akn:content GUID="ac1b7f17-5122-46de-a3b0-b35f13c17315" eId="hauptteil-1_art-4_abs-2_inhalt-1">
            <akn:p GUID="ad4ad1ec-08d7-494a-be96-14403d94decf" eId="hauptteil-1_art-4_abs-2_inhalt-1_text-1">
              This paragraph contains a "aenderungsbefehl-neufassung" mod that indicates that
              <akn:mod refersTo="aenderungsbefehl-neufassung" GUID="077716b9-0429-46e7-8ff0-a55e01110989" eId="hauptteil-1_art-4_abs-2_inhalt-1_text-1_ändbefehl-1">
                <akn:ref href="#" GUID="e7104abd-e51b-40a7-972f-e4b83b35efc7" eId="hauptteil-1_art-4_abs-2_inhalt-1_text-1_ändbefehl-1_ref-1">article 2</akn:ref>
                will be rewritten:
                <akn:quotedStructure startQuote="„" endQuote="“" GUID="8b2dedcd-94ff-48f7-b68b-3e855125ed6c" eId="hauptteil-1_art-4_abs-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1">
                  <akn:p GUID="3168cfb4-95ea-4ed3-9e2f-5fc0fc9e94e1" eId="hauptteil-1_art-4_abs-2_inhalt-1_text-1_ändbefehl-1_quotstruct-1_text-1"> The new structure of the article. </akn:p>
                </akn:quotedStructure>
              </akn:mod>
            </akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="4e4f5b9f-1050-48eb-9605-f83a426122dc" eId="hauptteil-1_art-4_abs-3">
          <akn:num GUID="f0f29c32-8725-4fb4-93a2-50436aa32bc6" eId="hauptteil-1_art-4_abs-3_bezeichnung-1"/>
          <akn:content GUID="5e847a45-b06c-435a-8808-0a123709cb5d" eId="hauptteil-1_art-4_abs-3_inhalt-1">
            <akn:p GUID="ec60856c-c553-45f0-96e9-69bfa6c6cab0" eId="hauptteil-1_art-4_abs-3_inhalt-1_text-1">
              This paragraph contains a "aenderungsbefehl-ersetzen" mod that indicates that
              <akn:mod refersTo="aenderungsbefehl-ersetzen" GUID="346ae7ab-e947-477c-8ca5-2d5c476cc789" eId="hauptteil-1_art-4_abs-3_inhalt-1_text-1_ändbefehl-1">in <akn:ref href="eli-noch-undefiniert" GUID="f06a5d32-8c50-461a-b1e8-62623b225bb7" eId="hauptteil-1_art-4_abs-3_inhalt-1_text-1_ändbefehl-1_ref-1">article 1</akn:ref> the word <akn:quotedText startQuote="„" endQuote="“" GUID="242428e1-75e6-420b-afae-e1825b1e0466" eId="hauptteil-1_art-4_abs-3_inhalt-1_text-1_ändbefehl-1_quottext-1">foo</akn:quotedText> will be replaced by <akn:quotedText startQuote="„" endQuote="“" GUID="2d25d3de-686b-4487-b687-9032fe1e9cc5" eId="hauptteil-1_art-4_abs-3_inhalt-1_text-1_ändbefehl-1_quottext-2">bar</akn:quotedText> .</akn:mod>
            </akn:p>
          </akn:content>
        </akn:paragraph>
        <akn:paragraph GUID="d2f8b1de-4ecb-4ba9-8cb3-b9d645f40781" eId="hauptteil-1_art-4_abs-4">
          <akn:num GUID="726283af-b24c-4da9-8fa6-a99df3989ac1" eId="hauptteil-1_art-4_abs-4_bezeichnung-1"/>
          <akn:content GUID="cc31c733-0a18-49d6-9704-8dcbbd3f6814" eId="hauptteil-1_art-4_abs-4_inhalt-1">
            <akn:p GUID="9e48dbf3-ee37-4743-9d1a-ce2d85193ff4" eId="hauptteil-1_art-4_abs-4_inhalt-1_text-1">
              This paragraph contains a "aenderungsbefehl-streichen" mod that indicates that
              <akn:mod refersTo="aenderungsbefehl-streichen" GUID="354634f2-a41e-4111-8de9-b061f0a6ede8" eId="hauptteil-1_art-4_abs-4_inhalt-1_text-1_ändbefehl-1">the articles <akn:ref href="eli-noch-undefiniert" GUID="3feda480-47c2-476b-8128-085d4c5b6b8f" eId="hauptteil-1_art-4_abs-4_inhalt-1_text-1_ändbefehl-1_ref-1">1 to 5</akn:ref> will be repealed.</akn:mod>
            </akn:p>
          </akn:content>
        </akn:paragraph>
      </akn:article>
    </akn:body>
  </akn:act>
</akn:akomaNtoso>');
