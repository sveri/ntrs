(ns de.sveri.ntrs.user
  (:require [reloaded.repl :refer [go reset]]
            [de.sveri.ntrs.components.components :refer [dev-system]]))

(defn start-dev-system []
  (go))

(reloaded.repl/set-init! dev-system)
