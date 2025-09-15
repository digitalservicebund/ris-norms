import { defineComponent, h } from "vue"

export const ButtonStub = defineComponent({
  name: "ButtonStub",
  props: {
    label: { type: String, default: "" },
    disabled: { type: Boolean, default: false },
    loading: { type: Boolean, default: false },
    text: { type: Boolean, default: false },
  },
  emits: ["click"],
  setup(props, { emit, slots }) {
    return () =>
      h(
        "button",
        {
          disabled: props.disabled || props.loading,
          "aria-busy": props.loading || undefined,
          "aria-label": props.label || undefined,
          onClick: (e: Event) => emit("click", e),
        },
        slots.default ? slots.default() : props.label,
      )
  },
})
