/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,vue,js,ts}"],
  presets: [require("@digitalservice4germany/style-dictionary/tailwind")],
  plugins: [require("@digitalservice4germany/angie")],
  theme: {
    extend: {
      colors: {
        highlight: {
          mod: {
            default: {
              hover: "rgba(196, 196, 196, 0.6)",
              selected: "rgba(224, 244, 255, 1)",
              default: "rgba(231, 231, 231, 0.4)",
            },
            1: {
              default: "#D0DFF0",
              hover: "#A6BCDD",
              selected: "#7999C8",
            },
            2: {
              default: "#E5F7FF",
              hover: "#B9E8FF",
              selected: "#78CCFF",
            },
            3: {
              default: "#DDFFF5",
              hover: "#B6FFE9",
              selected: "#77FFD3",
            },
            4: {
              default: "#E9FFE5",
              hover: "#C6FFBA",
              selected: "#81FF6E",
            },
            5: {
              default: "#FEF7BD",
              hover: "#FFED78",
              selected: "#F9D32A",
            },
            6: {
              default: "#FBEECE",
              hover: "#FFDDAD",
              selected: "#FFBB6B",
            },
            7: {
              default: "#FFE9E0",
              hover: "#FFCAC6",
              selected: "#FF9E98",
            },
            8: {
              default: "#FFEDFF",
              hover: "#FFCCFF",
              selected: "#FF9CFF",
            },
            9: {
              default: "#F7EDFF",
              hover: "#E4C2FF",
              selected: "#CD99FE",
            },
            10: {
              default: "#E8E4FF",
              hover: "#C5BCFF",
              selected: "#A496F0",
            },
          },
          affectedDocument: {
            hover: "rgba(255, 154, 251, 0.4)",
            selected: "rgba(255, 154, 251, 1)",
            default: "rgba(255, 154, 251, 0.2)",
            border: "#FE4AF7",
          },
        },
      },
    },
  },
  safelist: [
    // classes used dynamically by useModHighlightClasses
    {
      pattern: /bg-highlight-mod-(\d|default)-(default|hover|selected)/,
      variants: ["hover", "focus"],
    },
    {
      pattern: /border-highlight-mod-(\d|default)-selected/,
    },
    "border",
    "px-2",
    "border-dotted",
    "border-gray-900",
    "border-solid",
  ],
}
