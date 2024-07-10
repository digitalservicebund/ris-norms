import DB from "./db"
import * as fs from "node:fs"

const connectToDB = new DB()

async function establishDBConnection() {
  try {
    await connectToDB.getDBConnection()
  } catch (error) {
    console.log(
      `---------> X Failed to connect to dataBase <--------- \n\n ${error}`,
    )
    process.exit(1)
  }
}

async function resetDatabase() {
  await establishDBConnection()
  const sql = fs.readFileSync("e2e/database/initial_data.sql").toString()
  await connectToDB.executeQuery(sql)
}

export default resetDatabase
