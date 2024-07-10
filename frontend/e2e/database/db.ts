import pg from "pg"

export default class DB {
  private DBConfig = {
    user: "test",
    host: "localhost",
    database: "risnorms",
    password: "test",
    port: 5432,
    max: 10,
    idleTimeoutMillis: 30000,
    connectionTimeoutMillis: 2000,
    allowExitOnIdle: false,
  }

  private pool: pg.Pool = new pg.Pool(this.DBConfig)

  async getDBConnection(): Promise<pg.PoolClient> {
    if (!this.pool) {
      this.pool = new pg.Pool(this.DBConfig)
      const client = await this.pool.connect()
      console.log(`---------> √ DB connection has been established! <---------`)
      return client
    } else {
      return this.pool.connect()
    }
  }

  async executeQuery(query: string): Promise<void> {
    try {
      const client: pg.PoolClient = await this.getDBConnection()
      const result: pg.QueryResult = await client.query(query)
      console.log(result.rows)
    } catch (error) {
      console.error("Error executing query:", error)
    }
  }
}
