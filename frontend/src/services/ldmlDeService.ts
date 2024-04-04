export function evaluateXPath(xpath: string, xml: Document) {
  return xml
    .createExpression(xpath, (prefix) => {
      switch (prefix) {
        case "akn":
          return "http://Inhaltsdaten.LegalDocML.de/1.6/"
      }
      return null
    })
    .evaluate(xml)
    .iterateNext()
}

function getChangeTypeNode(xml: Document, eid: string) {
  return evaluateXPath(`//*[@eId="${eid}"]/@refersTo`, xml)
}

export function getChangeType(xml: Document, eid: string) {
  return getChangeTypeNode(xml, eid)?.nodeValue
}

export function setChangeType(
  xml: Document,
  eid: string,
  changeType: string,
): Document {
  const node = getChangeTypeNode(xml, eid)
  if (node) {
    node.nodeValue = changeType
  }
  return xml
}

export function getChangeRefHref(xml: Document, eid: string) {
  return evaluateXPath(`//*[@eId="${eid}"]/akn:ref/@href`, xml)?.nodeValue
}

function getChangeNewTextNode(xml: Document, eid: string) {
  return evaluateXPath(`(//*[@eId="${eid}"]/akn:quotedText)[2]`, xml)
}

export function getChangeNewText(xml: Document, eid: string) {
  return getChangeNewTextNode(xml, eid)?.textContent
}

export function getTargetLawHref(xml: Document, eid: string) {
  return evaluateXPath(`//*[@eId="${eid}"]/@href`, xml)?.nodeValue
}

export function setChangeNewText(
  xml: Document,
  eid: string,
  newText: string,
): Document {
  const node = getChangeNewTextNode(xml, eid)
  if (node) {
    node.textContent = newText
  }
  return xml
}
