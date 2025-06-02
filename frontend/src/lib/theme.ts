// This file is for making customizations to RIS UI specific to Norms. Ideally,
// these customizations don't remain here indefinitely, but will me transfered
// to RIS UI eventually.

import { RisUiTheme } from "@digitalservicebund/ris-ui/primevue"
import type {
  BadgePassThroughOptions,
  ToastPassThroughOptions,
  TreePassThroughOptions,
} from "primevue"
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
    class: tw`order-first relative top-6 rotate-270 group-aria-expanded:rotate-180 text-blue-800 flex-none`,
  },
}

const accordionContent: AccordionContentPassThroughOptions = {
  root: {
    class: tw`pl-[26px]`,
  },
}

const tree: TreePassThroughOptions = {
  node: {
    class: tw`mb-0 focus-visible:outline-4 focus-visible:outline-offset-4 focus-visible:outline-blue-800 [&>ul:first-of-type]:border-l-0`,
  },
}

const badge: BadgePassThroughOptions = {
  root: ({ props }) => {
    // Severities
    const noSeverity = tw`bg-gray-100`
    const success = tw`bg-green-100 text-green-800`
    const info = tw`bg-blue-300 text-blue-800`
    const warn = tw`bg-yellow-300`

    return {
      class: {
        [tw`rounded-full flex py-2 pl-4 pr-8 gap-4 items-center text-center`]:
          true,
        [noSeverity]: !props.severity,
        [success]: props.severity === "success",
        [info]: props.severity === "info",
        [warn]: props.severity === "warn",
      },
    }
  },
}

const toast: ToastPassThroughOptions = {
  transition: {
    enterFromClass: tw`opacity-0 translate-y-1/12`,
    enterActiveClass: tw`transition-all duration-150`,
    leaveFromClass: tw`max-h-[1000px]`,
    leaveActiveClass: tw`transition-all duration-150`,
    leaveToClass: tw`max-h-0 opacity-0 mb-0 overflow-hidden`,
  },
}

export default usePassThrough(
  RisUiTheme,
  {
    accordion,
    accordionHeader,
    accordionContent,
    badge,
    tree,
    toast,
  },
  { mergeProps: false, mergeSections: true },
)
