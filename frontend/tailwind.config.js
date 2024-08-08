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
              hover: "#dcdcdc",
              selected: "#e0f4ff",
              default: "#f5f5f5",
            },
            1: {
              default: "#D0F1F5",
              hover: "#88D7E3",
              selected: "#88D7E3",
            },
            2: {
              default: "#FEF4CA",
              hover: "#FAE17A",
              selected: "#FAE17A",
            },
            3: {
              default: "#EAD9FC",
              hover: "#C69CF8",
              selected: "#C69CF8",
            },
            4: {
              default: "#DDFDD8",
              hover: "#A7F998",
              selected: "#A7F998",
            },
            5: {
              default: "#FEE8D2",
              hover: "#FAC08F",
              selected: "#FAC08F",
            },
            6: {
              default: "#D6F7FE",
              hover: "#94E8FE",
              selected: "#94E8FE",
            },
            7: {
              default: "#FDDDDD",
              hover: "#FAA6A6",
              selected: "#FAA6A6",
            },
            8: {
              default: "#E0E0FB",
              hover: "#ADADF3",
              selected: "#ADADF3",
            },
            9: {
              default: "#D2FBF0",
              hover: "#8BF2D6",
              selected: "#8BF2D6",
            },
            10: {
              default: "#D8E6F2",
              hover: "#9DBBDC",
              selected: "#9DBBDC",
            },
          },
          affectedDocument: {
            hover: "rgba(255, 154, 251, 0.4)",
            selected: "rgba(255, 154, 251, 1)",
            default: "rgba(255, 154, 251, 0.2)",
            border: "#FE4AF7",
          },
          quotedStructure: {
            hover: {
              background: "#e5f7ff",
              border: "#4299f7",
              innerHover: {
                background: "#b9e8ff",
                border: "#4299f7",
              },
            },
            selected: {
              content: "#b9e8ff",
              border: "#004b76",
              pseudo: {
                background: "#78ccff",
                border: "#004b76",
              },
            },
          },
        },
      },
    },
  },
  safelist: [
    // classes used dynamically by useModHighlightClasses
    {
      pattern: /bg-highlight-mod-(\d+|default)-(default|hover|selected)/,
      variants: ["hover", "focus"],
    },
    {
      pattern: /border-highlight-mod-(\d+|default)-selected/,
    },
    "border",
    "px-2",
    "border-dotted",
    "border-gray-900",
    "border-solid",
  ],
}
