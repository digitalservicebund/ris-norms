import { defineStore } from "pinia"
import { ref } from "vue"
import { getProcedures, Procedure } from "@/services/proceduresService"

export const useProceduresStore = defineStore("procedures", () => {
  const procedures = ref<Procedure[]>([])

  async function loadProcedures() {
    procedures.value = await getProcedures()
  }

  function getProcedureByEli(eli: string) {
    return procedures.value.find((p) => p.eli === eli)
  }

  return { procedures, loadProcedures, getProcedureByEli }
})
