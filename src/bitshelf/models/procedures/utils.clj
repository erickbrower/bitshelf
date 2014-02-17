(in-ns 'bitshelf.models.db)

(defn all-tables []
  (->> 
    (-> ["SELECT table_name 
            FROM information_schema.tables 
            WHERE table_schema='public' 
            AND table_type='BASE TABLE'
            AND table_name <> 'ragtime_migrations';"]
      (exec-raw :results))
    (map :table_name)
    (into [])))

(defn clean-database []
  (map #(exec-raw [(str "TRUNCATE TABLE " % " CASCADE")]) (all-tables)))
