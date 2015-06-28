(ns de.sveri.ntrs.components.components
  (:require
    [com.stuartsierra.component :as component]
    (system.components
      [repl-server :refer [new-repl-server]])
    [de.sveri.ntrs.components.server :refer [new-web-server new-web-server-prod]]
    [de.sveri.ntrs.components.handler :refer [new-handler]]
    [de.sveri.ntrs.components.config :refer [new-config]]
    [de.sveri.ntrs.components.db :refer [new-db]]))


(defn dev-system []
  (component/system-map
    :config (new-config)
    :db (component/using (new-db) [:config])
    :handler (component/using (new-handler) [:config])
    :web (component/using (new-web-server) [:handler :config])))


(defn prod-system []
  (component/system-map
    :config (new-config)
    :db (component/using (new-db) [:config])
    :handler (component/using (new-handler) [:config])
    :web (component/using (new-web-server-prod) [:handler :config])))
