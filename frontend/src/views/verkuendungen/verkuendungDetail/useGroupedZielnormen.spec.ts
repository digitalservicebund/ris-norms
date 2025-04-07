import { describe, expect, it } from "vitest"
import { useGroupedZielnormen } from "./useGroupedZielnormen"
import { ref } from "vue"
import type { Norm } from "@/types/norm"

describe("useGroupedZielnormen", () => {
  it("returns empty array if the input is null", () => {
    const result = useGroupedZielnormen(null)
    expect(result.value).toEqual([])
  })

  it("returns empty array if the input is empty", () => {
    const result = useGroupedZielnormen(ref([]))
    expect(result.value).toEqual([])
  })

  it("returns a single group with data grouped by the ELI", () => {
    const data: Norm[] = [
      {
        eli: "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/regelungstext-1",
        fna: "754-28-1",
        frbrDateVerkuendung: "2017-03-15",
        frbrName: "BGBl. I",
        frbrNumber: "s593",
        shortTitle: "Vereinsgesetz",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
      },
      {
        eli: "eli/bund/bgbl-1/2017/s593/2017-03-16/1/deu/regelungstext-1",
        fna: "754-28-2",
        frbrDateVerkuendung: "2017-03-16",
        frbrName: "BGBl. I",
        frbrNumber: "s593",
        shortTitle: "Foo",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Foo",
      },
      {
        eli: "eli/bund/bgbl-1/2017/s593/2017-03-17/1/deu/regelungstext-1",
        fna: "754-28-3",
        frbrDateVerkuendung: "2017-03-17",
        frbrName: "BGBl. I",
        frbrNumber: "s593",
        shortTitle: "Bar",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Bar",
      },
    ]

    const result = useGroupedZielnormen(data)

    expect(result.value).toEqual([
      {
        eli: "eli/bund/bgbl-1/2017/s593",
        fna: "754-28-1",
        title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
        expressions: [
          {
            eli: "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/regelungstext-1",
            fna: "754-28-1",
            frbrDateVerkuendung: "2017-03-15",
            frbrName: "BGBl. I",
            frbrNumber: "s593",
            shortTitle: "Vereinsgesetz",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
          },
          {
            eli: "eli/bund/bgbl-1/2017/s593/2017-03-16/1/deu/regelungstext-1",
            fna: "754-28-2",
            frbrDateVerkuendung: "2017-03-16",
            frbrName: "BGBl. I",
            frbrNumber: "s593",
            shortTitle: "Foo",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Foo",
          },
          {
            eli: "eli/bund/bgbl-1/2017/s593/2017-03-17/1/deu/regelungstext-1",
            fna: "754-28-3",
            frbrDateVerkuendung: "2017-03-17",
            frbrName: "BGBl. I",
            frbrNumber: "s593",
            shortTitle: "Bar",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Bar",
          },
        ],
      },
    ])
  })

  it("returns a multipls groups with data grouped by the ELI", () => {
    const data: Norm[] = [
      {
        eli: "eli/bund/bgbl-1/2018/s500/2018-03-15/1/deu/regelungstext-1",
        fna: "754-28-1",
        frbrDateVerkuendung: "2018-03-15",
        frbrName: "BGBl. I",
        frbrNumber: "s500",
        shortTitle: "Vereinsgesetz",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
      },
      {
        eli: "eli/bund/bgbl-1/2020/s400/2020-03-16/1/deu/regelungstext-1",
        fna: "754-28-2",
        frbrDateVerkuendung: "2020-03-16",
        frbrName: "BGBl. I",
        frbrNumber: "s400",
        shortTitle: "Foo",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Foo",
      },
      {
        eli: "eli/bund/bgbl-1/2018/s300/2018-03-17/1/deu/regelungstext-1",
        fna: "754-28-3",
        frbrDateVerkuendung: "2018-03-17",
        frbrName: "BGBl. I",
        frbrNumber: "s300",
        shortTitle: "Bar",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Bar",
      },
    ]

    const result = useGroupedZielnormen(data)

    expect(result.value).toEqual([
      {
        eli: "eli/bund/bgbl-1/2018/s500",
        fna: "754-28-1",
        title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
        expressions: [
          {
            eli: "eli/bund/bgbl-1/2018/s500/2018-03-15/1/deu/regelungstext-1",
            fna: "754-28-1",
            frbrDateVerkuendung: "2018-03-15",
            frbrName: "BGBl. I",
            frbrNumber: "s500",
            shortTitle: "Vereinsgesetz",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
          },
        ],
      },
      {
        eli: "eli/bund/bgbl-1/2018/s300",
        fna: "754-28-3",
        title: "Gesetz zur Regelung des öffentlichen Bar",
        expressions: [
          {
            eli: "eli/bund/bgbl-1/2018/s300/2018-03-17/1/deu/regelungstext-1",
            fna: "754-28-3",
            frbrDateVerkuendung: "2018-03-17",
            frbrName: "BGBl. I",
            frbrNumber: "s300",
            shortTitle: "Bar",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Bar",
          },
        ],
      },
      {
        eli: "eli/bund/bgbl-1/2020/s400",
        fna: "754-28-2",
        title: "Gesetz zur Regelung des öffentlichen Foo",
        expressions: [
          {
            eli: "eli/bund/bgbl-1/2020/s400/2020-03-16/1/deu/regelungstext-1",
            fna: "754-28-2",
            frbrDateVerkuendung: "2020-03-16",
            frbrName: "BGBl. I",
            frbrNumber: "s400",
            shortTitle: "Foo",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Foo",
          },
        ],
      },
    ])
  })

  it("sorts expressions by date", () => {
    const data: Norm[] = [
      {
        eli: "eli/bund/bgbl-1/2017/s593/2017-03-17/1/deu/regelungstext-1",
        fna: "754-28-3",
        frbrDateVerkuendung: "2017-03-17",
        frbrName: "BGBl. I",
        frbrNumber: "s593",
        shortTitle: "Bar",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Bar",
      },
      {
        eli: "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/regelungstext-1",
        fna: "754-28-1",
        frbrDateVerkuendung: "2017-03-15",
        frbrName: "BGBl. I",
        frbrNumber: "s593",
        shortTitle: "Vereinsgesetz",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
      },
      {
        eli: "eli/bund/bgbl-1/2017/s593/2017-03-16/1/deu/regelungstext-1",
        fna: "754-28-2",
        frbrDateVerkuendung: "2017-03-16",
        frbrName: "BGBl. I",
        frbrNumber: "s593",
        shortTitle: "Foo",
        status: "status-not-yet-implemented",
        title: "Gesetz zur Regelung des öffentlichen Foo",
      },
    ]

    const result = useGroupedZielnormen(data)

    expect(result.value).toEqual([
      {
        eli: "eli/bund/bgbl-1/2017/s593",
        fna: "754-28-1",
        title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
        expressions: [
          {
            eli: "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/regelungstext-1",
            fna: "754-28-1",
            frbrDateVerkuendung: "2017-03-15",
            frbrName: "BGBl. I",
            frbrNumber: "s593",
            shortTitle: "Vereinsgesetz",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
          },
          {
            eli: "eli/bund/bgbl-1/2017/s593/2017-03-16/1/deu/regelungstext-1",
            fna: "754-28-2",
            frbrDateVerkuendung: "2017-03-16",
            frbrName: "BGBl. I",
            frbrNumber: "s593",
            shortTitle: "Foo",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Foo",
          },
          {
            eli: "eli/bund/bgbl-1/2017/s593/2017-03-17/1/deu/regelungstext-1",
            fna: "754-28-3",
            frbrDateVerkuendung: "2017-03-17",
            frbrName: "BGBl. I",
            frbrNumber: "s593",
            shortTitle: "Bar",
            status: "status-not-yet-implemented",
            title: "Gesetz zur Regelung des öffentlichen Bar",
          },
        ],
      },
    ])
  })
})
