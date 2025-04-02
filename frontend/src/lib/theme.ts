// This file is for making customizations to RIS UI specific to Norms. Ideally,
// these customizations don't remain here indefinitely, but will me transfered
// to RIS UI eventually.

import { RisUiTheme } from "@digitalservicebund/ris-ui/primevue"
import type { AccordionPassThroughOptions } from "primevue/accordion"
import type { AccordionContentPassThroughOptions } from "primevue/accordioncontent"
import type { AccordionHeaderPassThroughOptions } from "primevue/accordionheader"
import { usePassThrough } from "primevue/passthrough"
import { tw } from "./tw"

const accordion: AccordionPassThroughOptions = {
  root: {
    class: tw`bg-white border-t border-b border-blue-300 divide-y divide-blue-300`,
  },
}

const accordionHeader: AccordionHeaderPassThroughOptions = {
  root: {
    class: tw`group flex gap-14 px-12 py-8 justify-start text-left w-full cursor-pointer disabled:cursor-not-allowed disabled:text-gray-600 focus-visible:outline-4 outline-blue-800 -outline-offset-4 hover:bg-gray-200`,
  },
  toggleicon: {
    class: tw`order-first relative top-6 rotate-270 group-aria-expanded:rotate-180 text-blue-800`,
  },
}

const accordionContent: AccordionContentPassThroughOptions = {
  root: {
    class: tw`pl-[26px]`,
  },
}

export default usePassThrough(
  RisUiTheme,
  {
    accordion,
    accordionHeader,
    accordionContent,
  },
  { mergeProps: false, mergeSections: true },
)
