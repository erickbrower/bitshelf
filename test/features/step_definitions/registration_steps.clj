(require '[clj-webdriver.taxi :as taxi]
         '[bitshelf.browser :refer [browser-up browser-down]]
         '[clojure.test :refer :all]
         '[bitshelf.models.db :as db])

(Given #"^I am at the \"registration\" page$" []
  (browser-up)
  (taxi/to "http://localhost:3000/register"))

(When #"^I fill in \"([^\"]*)\" with \"([^\"]*)\"$" [field value]
  (taxi/input-text field value))

(When #"^I click on \"([^\"]*)\"$" [thing]
  (taxi/click thing))

(Then #"^I should see \"([^\"]*)\"$" [title]
      (is (= (taxi/text "div.jumbotron > h1") title))
      (browser-down))

(Then #"^I should see errors$" []
      (is (= 1 1)))
