package com.redhat.qe.se.locators


import com.redhat.qe.auto.selenium.Element
import com.redhat.qe.auto.selenium.LocatorStrategy
import com.redhat.qe.auto.selenium.LocatorTemplate
import com.redhat.qe.auto.selenium.TabElement
import com.redhat.qe.se.base.SeleniumTestScript

public class UIElements extends UILocatorStrategies{
    //NAVIGATION
    //    def tabs = [ "Dashboard", "Content Management", "Systems", "Organizations", "Administration"]
    //    TabElement dashboard = new TabElement(notSelectedNavLink, selectedNavLink, "Adminstration")
    //    def administrationSubtabs = [ "Users", "Roles" ]

    def tab = [ "Tab"]
    
    def tabs = { keys ->
        sameName( )
    }
    
    def roles = [ 
        newRole : "//a[@id='new']",
        newRoleNameText : "role[name]",
        newRoleDescriptionText : "role[description]",
        saveRole : "role_save",
        saveUserEdit : "save_password",
        roleUsers : "role_users",
        "next" : "next_button",
        permissionResourceTypeSelect : "permission[resource_type_attributes[name]]",
        permissionVerbSelect : "permission[verb_values][]",
        permissionTagSelect : "tags",
        permissionNameText : "permission[name]",
        permissionDescriptionText : "permission[description]",
        savePermission : "save_permission_button",
        removeRole : "remove_role",
        addPermission : "add_permission"
    ]

    def navigation = new NodeBuilder().topLevel() {
        dashboardTab()
        contentManagementTab() {
            contentProvidersTab() {
                customContentProvidersTab() {
                    newProviderPage()
                    namedProviderPage() {
                        providerProductsReposPage() {
                            namedProductPage()
                            namedRepoPage()
                        }
                        providerDetailsPage()
                        providerSubscriptionsPage()
                    }
                }
                redhatProviderTab()
            }
            syncManagementPage() {
                syncStatusPage()
                syncPlansPage() {
                    namedSyncPlanPage()
                    newSyncPlanPage()
                }
                syncSchedulePage()
            }
            promotionsPage() {
                namedEnvironmentPromotionsPage() { namedChangesetPromotionsPage() }
            }
            systemTemplatesPage() {
                namedSystemTemplatePage()
                newSystemTemplatePage()
            }
        }
        systemsTab() {
            systemsAllPage() {
                systemsByEnvironmentPage() {
                    namedSystemsPage() { systemSubscriptionsPage() }
                }
            }
            activationKeysPage() {
                namedActivationKeyPage()
                newActivationKeyPage()
            }
            systemsEnvironmentPage() { namedSystemEnvironmentPage() }
        }
        organizationsTab() {
            newOrganizationPage()
            namedOrganizationPage() {
                newEnvironmentPage()
                namedEnvironmentPage()
            }
        }
        administrationTab() {
            usersTab() {
                namedUserPage() {
                    userEnvironmentsPage()
                    userRolesPermissionsPage()
                }
            }
            rolesTab() {
                namedRolePage() {
                    namedRoleUsersPage()
                    namedRolePermissionsPage()
                }
            }
        }
    }
    //    tab_elements = {'dashboard_tab' : (By.XPATH, "//a[.='Dashboard']"),
    //                             'content_management_tab' : (By.XPATH, "//a[.='Content Management']"),
    //                             'providers' : (By.XPATH, "//a[.='Content Providers']"),
    //                             'systems_tab' : (By.XPATH, "//a[.='Systems']"),
    //                             'systems_all' : (By.XPATH, "//a[.='All']"),
    //                             'systems_by_environment' : (By.XPATH, "//a[.='By Environments']"),
    //                             'activation_keys' : (By.XPATH, "//a[.='Activation Keys']"),
    //                             'organizations_tab' : (By.XPATH, "//a[.='Organizations']"),
    //                             'organizations_all' : (By.XPATH, "//a[.='List']"),
    //                             'organizations_subscriptions' : (By.XPATH, "//a[.='Subscriptions']"),
    //                             'administration_tab' : (By.XPATH, "//a[.='Administration']"),
    //                             'users_administration': (By.XPATH, "//a[.='Users']"),
    //                             'roles_administration' : (By.XPATH, "//a[.='Roles']"),}
    //      (defmacro define-strategies
    //        "Expands into a function for each locator strategy in map m (which
    //        maps symbol to LocatorStrategy). Each function will be named the
    //        same as the symbol, take arguments and return a new element
    //        constructed with the locator strategy and args. See also the
    //        LocatorTemplate class."
    //        [m]
    //        `(do ~@(for [loc-strat (keys m)]
    //                 `(def ~loc-strat
    //                    (template ~@(m loc-strat))))))
    //    def strategies = { m ->
    //        m.keys.each { loc-strat ->
    //            def ${loc-strat} = template(m, loc-strat)
    //        }
    //    }
    //
    //      (define-strategies
    //        {add-repository ["Add Repository" "//div[@id='products']//div[contains(.,'$1')]/..//div[normalize-space(.)='Add Repository' and contains(@class, 'button')]"]
    //         button-div ["Button"
    //                     "//div[contains(@class,'button') and normalize-space(.)='$1']"]
    //         changeset ["Changeset"
    //                    "//div[starts-with(@id,'changeset_') and normalize-space(.)='$1']"]
    //         changeset-status ["Changeset status"  "//span[.='$1']/..//span[@class='changeset_status']"]
    //         editable ["Editable" "//div[contains(@class, 'editable') and descendant::text()[substring(normalize-space(),2)='$1']]"]
    //
    //         environment-link ["Environment"
    //                           "//div[contains(@class,'jbreadcrumb')]//a[normalize-space(.)='$1']"]
    //
    //         link ["" "link=$1"]
    //         notification-close-index ["Notification close button"
    //                                   "xpath=(//div[contains(@class,'jnotify-notification-error')]//a[@class='jnotify-close'])[$1]"]
    //         org-switcher ["Org switch list" "//div[@id='orgbox']//a[.='$1']"]
    //         permission-org ["Permission Org" "//li[@class='slide_link' and starts-with(normalize-space(.),'$1')]"]
    //
    //         plus-icon ["Plus icon" "//li[.='$1']//span[contains(@class,'ui-icon-plus')]"]
    //         product-edit ["Product edit"
    //                       "//div[@id='products']//div[contains(@data-url, 'edit') and contains(.,'$1')]"]
    //         product-expand ["Expand product"
    //                         "//div[@id='products']//div[contains(@data-url,'products') and contains(.,'$1')]/..//img[@alt='Expand']"]
    //         product-schedule ["Schedule for product" "//div[normalize-space(.)='$1']/following-sibling::div[1]"]
    //         schedule ["Product to schedule" "//div[normalize-space(.)='$1']"]
    //         promotion-add-content-item ["Add Content Item"
    //                                     "//a[@data-display_name='$1' and contains(.,'Add')]"]
    //         promotion-content-category ["Content Category" "//div[@id='$1']"]
    //         promotion-content-item-n ["Content item by index"
    //                                  "//div[@id='list']//li[$1]//div[contains(@class,'simple_link')]/descendant::text()[(position()=0 or parent::span) and string-length(normalize-space(.))>0]"]
    //         promotion-remove-content-item ["Remove Content Item"
    //                                        "//a[@data-display_name='$1' and contains(.,'Remove')]"]
    //         provider-sync-checkbox ["Provider sync checkbox"
    //                                 "//table[@id='products_table']//label[normalize-space(.)='$1']/..//input"]
    //         provider-sync-progress ["Provider progress"
    //                                 "//tr[td/label[normalize-space(.)='$1']]/td[5]"]
    //         repo-enable-checkbox ["Repo enable checkbox" "//table[@id='products_table']//label[normalize-space(.)='$1']/..//input"]
    //         role-action ["Role action" "//li[.//span[@class='sort_attr' and .='$2']]//a[.='$1']"]
    //         slide-link ["Slide Link" "//li[contains(@class,'slide_link') and normalize-space(.)='$1']"]
    //         subscription-checkbox ["Subscription checkbox" "//div[@id='panel-frame']//td[contains(normalize-space(.),'$1')]//input[@type='checkbox']"]
    //         sync-plan ["Sync Plan" "//div[@id='plans']//div[normalize-space(.)='$1']"]
    //         tab ["Tab" "link=$1"]
    //         template-product ["Template product" "//span[contains(@class, 'custom-product-sprite')]/following-sibling::span/text()[contains(.,'$1')]"]
    //         template-action ["Template content action" "//a[@data-name='$2' and .='$1']"]
    //         template-eligible-category ["Template category" "//div[@id='content_tree']//div[normalize-space()='$1']"]
    //         textbox ["" "xpath=//*[self::input[(@type='text' or @type='password' or @type='file') and @name='$1'] or self::textarea[@name='$1']]"]
    //         user ["User" "//div[@id='list']//div[contains(@class,'column_1') and normalize-space(.)='$1']"]
    //         username-field ["Username field" "//div[@id='users']//div[normalize-space(.)='$1']"]
    //         left-pane-field-list ["Left pane item#" "xpath=(//div[contains(@class,'ellipsis')])[$1]"]})
    //
    //      (defn- tabs
    //        "Takes a list of keywords, and creates mapping eg: {:my-tab 'link=My Tab'}"
    //        [keys]
    //        (same-name capitalize tab keys))
    //
    //      ;;
    //      ;;UI locators - mapping of names to selenium locator strings.
    //      ;;
    //
    //      (def common {:notification "//div[contains(@class,'jnotify-notification')]"
    //                   :error-message "//div[contains(@class,'jnotify-notification-error')]"
    //                   :success-message "//div[contains(@class,'jnotify-notification-message')]"
    //                   :spinner "//img[contains(@src,'spinner.gif')]"
    //                   :save-inplace-edit "//button[.='Save']"
    //                   :confirmation-dialog "//div[contains(@class, 'confirmation')]"
    //                   :confirmation-yes "//div[contains(@class, 'confirmation')]//span[.='Yes']"
    //                   :confirmation-no "//div[contains(@class, 'confirmation')]//span[.='No']"
    //                   :search-bar "search"
    //                   :search-submit "//button[@form='search_form']"
    //                   ;;main banner
    //                   :account "//li[@class='hello']/a"
    //                   :log-out "//a[normalize-space(.)='Logout']"
    //                   :org-switcher "switcherButton"
    //                   :active-org "//*[@id='switcherButton']/div[1]"
    //                   })
    //
    //      (def all-tabs (tabs [:organizations
    //                           :administration
    //                           :systems
    //                           :content-management
    //                           :dashboard
    //                           :all
    //                           :by-environments
    //                           :create
    //
    //                           ;;subtabs
    //                           :content-providers
    //                           :custom-content-providers
    //                           :red-hat-content-provider
    //                           :sync-management
    //                           :sync-status
    //                           :sync-plans
    //                           :sync-schedule
    //                           :promotions
    //                           :system-templates
    //                           :users
    //                           :roles
    //                           :activation-keys
    //                           :details
    //
    //                           :registered
    //                           :groups
    //                           :general
    //                           :subscriptions
    //                           :facts
    //                           :packages]))
    //
    //      (def organizations {:new-organization "//a[@id='new']"
    //                          :create-organization "organization_save"
    //                          :org-name-text (textbox "name")
    //                          :org-description-text (textbox "description")
    //                          :org-environments (link "Environments")
    //                          :edit-organization (link "Edit")
    //                          :remove-organization (link "Remove Organization")
    //                          :org-description-text-edit "organization[description]"})
    //
    //      (def environments {:env-name-text (textbox "name")
    //                         :env-description-text (textbox "description")
    //                         :prior-environment "//select[@id='prior']"
    //                         :create-environment "//input[@value='Create']"
    //                         :new-environment "//div[normalize-space(.)='Add New Environment']"
    //                         :remove-environment (link "Remove Environment")
    //                         :env-name-text-edit "kt_environment[name]"
    //                         :env-description-text-edit "kt_environment[description]"
    //                         :env-prior-select-edit "kt_environment[prior]" })
    //
    //      (def providers {:new-provider "new"
    //                      :provider-name-text  "provider[name]"
    //                      :provider-description-text "provider[description]"
    //                      :provider-repository-url-text "provider[repository_url]"
    //                      :provider-cert-text (textbox "provider[certificate_attributes][contents]")
    //                      :provider-create-save "provider_save"
    //                      :remove-provider (link "Remove Provider")
    //                      :subscriptions (link "Subscriptions")
    //                      :redhat-provider-repository-url-text "provider[repository_url]"
    //                      :choose-file "provider_contents"
    //                      :upload "upload_submit"
    //                      :enable-repositories-tab "//a[normalize-space(.)='Enable Repositories']"
    //                      :products-and-repositories "//nav[contains(@class,'subnav')]//a[contains(.,'Products')]"
    //
    //                      ;;add product
    //                      :add-product (button-div "Add Product")
    //                      :create-product "//input[@value='Create']"
    //                      :product-name-text "//*[@name='product[name]']"
    //                      :product-description-text "//*[@name='product[description]']"
    //                      :remove-product (link "Remove Product")
    //                      ;;add repo
    //                      :add-repository "//ul[//div[starts-with(@id,'edit_product') and normalize-space(.)='$1']]//div[starts-with(@id,'add_repository')]"
    //                      :repo-name-text "repo[name]"
    //                      :repo-url-text "repo[feed]"
    //                      :save-repository "//input[@value='Create']"
    //                      :remove-repository (link "Remove Repository")
    //
    //                      ;;redhat page
    //                      :subscriptions-items "//table[@id='redhatSubscriptionTable']/tbody/tr"
    //                      })
    //
    //      (def promotions {:products-category (promotion-content-category "products")
    //                       :expand-path "path-collapsed"
    //                       :errata-category (promotion-content-category "errata")
    //                       :packages-category (promotion-content-category "packages")
    //                       :kickstart-trees-category (promotion-content-category "kickstart trees")
    //                       :templates-category (promotion-content-category "templates")
    //                       :promotion-eligible-home "//div[@id='content_tree']//span[contains(@class,'home_img_inactive')]"
    //
    //                       :review-for-promotion "review_changeset"
    //                       :promote-to-next-environment "//div[@id='promote_changeset' and not(contains(@class,'disabled'))]"
    //                       :promotion-empty-list "//div[@id='left_accordion']//ul[contains(.,'available for promotion')]"
    //                       :new-promotion-changeset "//a[contains(.,'New Promotion Changeset')]"
    //                       :changeset-name-text (textbox "name")
    //                       :save-changeset "save_changeset_button"
    //                       :changeset-content "//div[contains(@class,'slider_two') and contains(@class,'has_content')]"})
    //
    //      (def users {:roles-subsubtab "//div[@class='panel-content']//a[.='Roles']"
    //                  :environments-subsubtab "//div[@class='panel-content']//a[.='Environments']"
    //                  :user-default-org-select "org_id[org_id]"
    //                  :save-user-environment "update_user"
    //                  :new-user "//a[@id='new']"
    //                  :new-user-username-text "username_field"
    //                  :new-user-password-text "password_field"
    //                  :new-user-confirm-text "confirm_field"
    //                  :new-user-default-org "org_id[org_id]"
    //                  :new-user-email "email_field"
    //                  :save-user "save_user"
    //                  :remove-user (link "Remove User")
    //                  :enable-inline-help-checkbox "user[helptips_enabled]"
    //                  :clear-disabled-helptips "clear_helptips"
    //                  :change-password-text "password_field"
    //                  :confirm-password-text "confirm_field"
    //                  :user-email-text "user[email]"
    //                  :save-roles "save_roles"
    //                  :add-all (link "Add all")
    //                  :password-conflict "//div[@id='password_conflict' and string-length(.)>0]"})
    //
    //      (def sync-plans {:new-sync-plan "new"
    //                       :sync-plan-name-text "sync_plan[name]"
    //                       :sync-plan-description-text "sync_plan[description]"
    //                       :sync-plan-interval-select "sync_plan[interval]"
    //                       :sync-plan-date-text "sync_plan[plan_date]"
    //                       :sync-plan-time-text "sync_plan[plan_time]"
    //                       :save-sync-plan "plan_save"})
    //
    //      (def systems {:system-name-text-edit "system[name]"
    //                    :system-description-text-edit "system[description]"
    //                    :system-location-text-edit "system[location]"
    //                    ;;subscriptions pane
    //                    :subscribe "commit"
    //
    //                    ;;Activationkeys subtab
    //                    :new-activation-key "new"
    //                    :activation-key-name-text "activation_key[name]"
    //                    :activation-key-description-text "activation_key[description]"
    //                    :activation-key-template-select "activation_key[system_template_id]"
    //                    :save-activation-key "save_key"
    //                    :remove-activation-key (link "Remove Activation Key")
    //                    :subscriptions-right-nav "//div[contains(@class, 'panel-content')]//a[.='Subscriptions']"})
    //
    //      (def roles {:new-role "//a[@id='new']"
    //                  :new-role-name-text "role[name]"
    //                  :new-role-description-text "role[description]"
    //                  :save-role "role_save"
    //                  :save-user-edit "save_password"
    //                  :role-users "role_users"
    //                  :role-permissions "role_permissions"
    //                  :next "next_button"
    //                  :permission-resource-type-select "permission[resource_type_attributes[name]]"
    //                  :permission-verb-select "permission[verb_values][]"
    //                  :permission-tag-select "tags"
    //                  :permission-name-text "permission[name]"
    //                  :permission-description-text "permission[description]"
    //                  :save-permission "save_permission_button"
    //                  :remove-role "remove_role"
    //                  :add-permission "add_permission"})
    //
    //      (def sync-schedules {:apply-sync-schedule "apply_button"})
    //
    //      (def templates {:new-template "new"
    //                      :template-name-text "system_template[name]"
    //                      :template-description-text "system_template[description]"
    //                      :save-new-template "template_save" ;;when creating
    //                      :template-eligible-package-groups (template-eligible-category "Package Groups")
    //                      :template-eligible-packages (template-eligible-category "Packages")
    //                      :template-eligible-repositories (template-eligible-category "Repositories")
    //                      :template-package-groups (slide-link "Package Groups")
    //                      :template-eligible-home "//div[@id='content_tree']//span[contains(@class,'home_img_inactive')]"
    //                      :save-template "save_template"}) ;;when editing
    //
    //      ;;merge all the preceeding maps together, plus a few more items.
    //      (def ^{:doc "All the selenium locators for the Katello UI. Maps a
    //        keyword to the selenium locator. You can pass the keyword to
    //        selenium just the same as you would the locator string. See also
    //        SeleniumLocatable protocol."}
    //        uimap
    //        (merge all-tabs common organizations environments roles users systems sync-plans
    //               sync-schedules promotions providers templates
    //               { ;; login page
    //                :username-text (textbox "username")
    //                :password-text (textbox "password")
    //                :log-in "//input[@value='Log In']"
    //
    //
    //                ;;tabs with special chars in name
    //                :sub-organizations (tab "Sub-Organizations")
    //
    //
    //                ;;Sync Management subtab
    //                :synchronize-now "sync_button"}))
    //
    //      ;;Tells the clojure selenium client where to look up keywords to get
    //      ;;real selenium locators (in uimap in this namespace).
    //      (extend-protocol SeleniumLocatable
    //        clojure.lang.Keyword
    //        (sel-locator [k] (uimap k))
    //        String
    //        (sel-locator [x] x))
    //
    //      (defn promotion-env-breadcrumb
    //        "Locates a link in the environment breadcrumb UI widget. If there
    //        are multiple environment paths, and you wish to select Library,
    //        'next' is required."
    //        [name & [next]]
    //        (let [prefix "//a[normalize-space(.)='%1$s' and contains(@class, 'path_link')]"]
    //          (Element. (format
    //                     (str prefix (if next "/../../..//a[normalize-space(.)='%1$s']" ""))
    //                     name next))))
    //
    //      (defn inactive-edit-field
    //        "Takes a locator for an active in-place edit field, returns the
    //        inactive version"
    //        [loc]
    //        (format "//div[@name='%1s']" (sel-locator loc)))
    //
    //      (defn left-pane-item
    //        "Returns a selenium locator for an item in a left
    //         pane list (by the name of the item)"
    //        [name]
    //        (Element. (LocatorTemplate. "Left pane item"
    //                                    "//div[@id='list']//div[starts-with(normalize-space(.),'$1')]")
    //                  (into-array [(let [l (.length name)]
    //                                 (if (> l 32)
    //                                   (.substring name 0 32) ;workaround for bz 737678
    //                                   name))])))
    //
    //
    //      ;;nav tricks
    //      (defn select-environment-widget [env-name & [{:keys [next-env-name]}]]
    //        (do (when (browser isElementPresent :expand-path)
    //              (browser click :expand-path))
    //            (browser click (promotion-env-breadcrumb env-name next-env-name))))
    //
    //      (defn search [search-term]
    //        (fill-form {:search-bar search-term}
    //                   :search-submit (constantly nil)))
    //
    //      (defn choose-left-pane
    //        "Selects an item in the left pane. If the item is not found, a
    //         search is performed and the select is attempted again. Takes an
    //         optional post-fn to perform afterwards."
    //        [item & [post-fn]]
    //        (try (browser click item)
    //             (catch SeleniumException se
    //               (do (search (-> item .getArguments first))
    //                   (browser click item)))
    //             (finally ((or post-fn no-wait)))))
    //
    //      (defn toggler
    //        "Returns a function that returns a locator for the given on/off text
    //         and locator strategy. Used for clicking things like +Add/Remove for
    //         items in changesets or permission lists."
    //        [[on-text off-text]
    //         loc-strategy]
    //        (fn [associated-text on?]
    //          (loc-strategy (if on? on-text off-text) associated-text)))
    //
    //      (def add-remove ["+ Add" "Remove"])
    //
    //      (def user-role-toggler (toggler add-remove role-action))
    //      (def template-toggler (toggler add-remove template-action))
    //
    //
    //      (defn toggle "Toggles the item from on to off or vice versa."
    //        [a-toggler associated-text on?]
    //        (browser click (a-toggler associated-text on?)))
    //
    //      ;;
    //      ;;Navigation tree - shows all the navigation paths through the ui.
    //      ;;this data is used by the katello.tasks/navigate function to get to
    //      ;;the given page.
    //      (def
    //        ^{:doc "The navigation layout of the UI. Each item in the tree is
    //        a new page or tab, that you can drill down into from its parent
    //        item. Each item contains a keyword to refer to the location in the
    //        UI, a list of any arguments needed to navigate there (for example,
    //        to navigate to a provider details page, you need the name of the
    //        provider). Finally some code to navigate to the location from its
    //        parent location. See also katello.tasks/navigate."}
    //        page-tree
    //        (page-zip (nav-tree
    //          [:top-level [] (if (or (not (browser isElementPresent :log-out))
    //                                 (browser isElementPresent :confirmation-dialog))
    //                           (browser open (@config :server-url)))
    //           [:content-management-tab [] (browser mouseOver :content-management)
    //            [:content-providers-tab [] (browser mouseOver :content-providers)
    //             [:custom-content-providers-tab [] (browser clickAndWait :custom-content-providers)
    //              [:new-provider-page [] (browser click :new-provider)]
    //              [:named-provider-page [provider-name] (choose-left-pane (left-pane-item provider-name))
    //               [:provider-products-repos-page [] (->browser (click :products-and-repositories)
    //                                                            (sleep 2000))
    //                [:named-product-page [product-name] (browser click (editable product-name))]
    //                [:named-repo-page [product-name repo-name] (browser click (editable repo-name))]]
    //               [:provider-details-page [] (browser click :details)]
    //               [:provider-subscriptions-page [] (browser click :subscriptions)]]]
    //             [:redhat-provider-tab [] (browser clickAndWait :red-hat-content-provider)]]
    //            [:sync-management-page [] (browser mouseOver :sync-management)
    //             [:sync-status-page [] (browser clickAndWait :sync-status)]
    //             [:sync-plans-page [] (browser clickAndWait :sync-plans)
    //              [:named-sync-plan-page [sync-plan-name]
    //               (choose-left-pane (left-pane-item sync-plan-name))]
    //              [:new-sync-plan-page [] (browser click :new-sync-plan)]]
    //             [:sync-schedule-page [] (browser clickAndWait :sync-schedule)]]
    //            [:promotions-page [] (browser clickAndWait :promotions)
    //             [:named-environment-promotions-page [env-name next-env-name]
    //              (select-environment-widget env-name {:next-env-name next-env-name
    //                                                   :wait-fn load-wait})
    //              [:named-changeset-promotions-page [changeset-name]
    //               (browser click (changeset changeset-name))]]]
    //            [:system-templates-page [] (browser clickAndWait :system-templates)
    //             [:named-system-template-page [template-name] (browser click (slide-link template-name))]
    //             [:new-system-template-page [] (browser click :new-template)]]]
    //           [:systems-tab [] (browser mouseOver :systems)
    //            [:systems-all-page [] (browser clickAndWait :all)
    //             [:systems-by-environment-page [] (browser clickAndWait :by-environments)
    //              [:named-systems-page [system-name] (choose-left-pane
    //                                                  (left-pane-item system-name))
    //               [:system-subscriptions-page [] (browser click :subscriptions-right-nav)]]]]
    //            [:activation-keys-page [] (browser clickAndWait :activation-keys)
    //             [:named-activation-key-page [activation-key-name]
    //              (choose-left-pane (left-pane-item activation-key-name))]
    //             [:new-activation-key-page [] (browser click :new-activation-key)]]
    //            [:systems-environment-page [env-name]
    //             (do (browser clickAndWait :by-environments)
    //                 (select-environment-widget env-name))
    //             [:named-system-environment-page [system-name]
    //              (choose-left-pane (left-pane-item system-name))]]]
    //           [:organizations-tab [] (browser clickAndWait :organizations)
    //            [:new-organization-page [] (browser click :new-organization)]
    //            [:named-organization-page [org-name] (choose-left-pane (left-pane-item org-name))
    //             [:new-environment-page [] (browser click :new-environment)]
    //             [:named-environment-page [env-name] (browser click (environment-link env-name))]]]
    //           [:administration-tab [] (browser mouseOver :administration)
    //            [:users-tab [] (browser clickAndWait :users)
    //             [:named-user-page [username] (choose-left-pane (user username))
    //              [:user-environments-page [] (browser click :environments-subsubtab)]
    //              [:user-roles-permissions-page [] (browser click :roles-subsubtab)]]]
    //            [:roles-tab [] (browser clickAndWait :roles)
    //             [:named-role-page [role-name] (choose-left-pane (left-pane-item role-name))
    //              [:named-role-users-page [] (browser click :role-users)]
    //              [:named-role-permissions-page [] (browser click :role-permissions)]]]]])))
    //
    //      (def tab-list '(:redhat-provider-tab
    //                      :roles-tab :users-tab
    //                      :systems-all-page
    //                      :activation-keys-page
    //                      :systems-by-environment-page))
    //GENERIC
    public Element checkboxGeneric = new Element(type,"checkbox")

    // used to recover from significant errors like a pgError exception
    public static final String url_base = SeleniumTestScript.ceServerPath

    //Login Page
    public Element LoginUserName = new Element(name,"login")
    public Element LoginUserPasswd = new Element(name,"password")
    //public Element Login = new Element(id,"user_session_submit");
    public Element Login = new Element(button,"Login")
    public Element SelfServiceCreateAccount = new Element("//a[@class='actionlink']")

    public Element SSLUnderstandRisks = new Element("//*[normalize-space(@id)='expertContentHeading']")
    public Element SSLExceptionAccept = new Element("//*[normalize-space(@id)='exceptionDialogButton']")
    //End Login Page


    //Dashboard
    public TabElement Dashboard = new TabElement(notSelectedNavLink, selectedNavLink, "Dashboard")
    //public TabElement InstanceManagement = new TabElement(notSelectedNavLink, selectedNavLink,"Instance Management");
    public Element Templates_static = new Element(link,"Templates")
    //public TabElement Templates = new TabElement(notSelectedNavLink, selectedNavLink,"Deployables");
    public TabElement PoolsZones = new TabElement(notSelectedNavLink, selectedNavLink,"Pools & Zones")
    public TabElement Settings = new TabElement(notSelectedNavLink, selectedNavLink,"System Settings")
    public TabElement Builds = new TabElement(notSelectedNavLink, selectedNavLink,"Builds")

    //public Element Summary = new Element(link,"Summary");
    //public Element Alerts = new Element(link,"Alerts");
    //public Element ServiceQuality = new Element(link,"Service Quality");
    //public Element QuotaUsage = new Element(link,"Quota Usage");
    //public Element Billing = new Element(link,"Billing");
    //public Element HelpTickets = new Element(link,"Help Tickets");
    //End Dashboard


    //Settings
    //public Element manageUsersURL = new Element(home + "users");
    //public TabElement manageUsers = new TabElement(notSelectedNavLink, selectedNavLink,"Manage Users");
    public TabElement permissions = new TabElement(notSelectedNavLink, selectedNavLink,"Permissions")
    //public TabElement manageProviders = new TabElement(notSelectedNavLink, selectedNavLink,"Manage Providers");
    //public Element selfServiceSettings = new Element(nav_href,"subnav", url_base + "settings/self_service");

    //End Settings

    //Permissions
    public Element quota_self_service = new Element(id,"self_service_default_quota_maximum_running_instances")
    //End Permissions

    //Manage Users
    //public Element createUser = new Element(link,"create");
    public Element chooseUsername = new Element(id,"user_login")
    public Element choosePassword = new Element(id,"user_password")
    public Element confirmPassword = new Element(id,"user_password_confirmation")
    public Element firstName = new Element(id,"user_first_name")
    public Element lastName = new Element(id,"user_last_name")
    public Element email = new Element(id,"user_email")
    public Element user_save = new Element(id,"user_submit")
    public Element user_quota_set = new Element(id,"user_quota_attributes_maximum_running_instances")

    public LocatorStrategy lsUserTableCell = new LocatorTemplate('lsUserTableCell','''//table[@id='users_table']/tbody/tr/td/a[normalize-space(.)='$1']/../../td[position()='$2']''')
    public Element accountManage = new Element("//li[@Class='account']")
    public Element logout = new Element(link,"Log out")
    public Element myAccount = new Element(link,"My Account")
    public Element userCardusername = new Element("//div[@Class='user-card-content']/dl/dd[2]")

    //public Element deleteUser = new Element(button,"delete");


    //End Manage Users

    //Manage Providers
    public Element ProviderAccount_Provider = new Element(id,"provider_account_provider_id")
    public Element providerAcct_h1 = new Element(h1,"PROVIDER ACCOUNTS")
    //public Element addProvider = new Element(id_button,"providers_nav","Add");
    //public Element editProvider = new Element(id_link,"providers_nav","Edit");
    public Element saveProvider = new Element(id_button,"details","Edit")
    public Element providerSummary = new  Element(link,"Provider Summary")
    //public Element providerAccounts = new Element(link,"Provider Accounts");
    public Element providerAccount = new Element(link,"Provider Account")
    public Element providerName = new Element(id,"provider_name")
    public Element providerUrl = new Element(id,"provider_url")
    public Element testConnection = new Element(id,"test_connection")
    public Element testAccount = new Element(button,"Test Account")
    //public Element addProviderSubmit = new Element(id,"add_provider");
    //public Element saveProviderSubmit = new Element(id,"save_provider");
    public Element providerAccountName = new Element(id,"provider_account_label")
    public Element providerUserName = new Element(name,"provider_account[credentials_hash][username]")
    public Element providerUserPasswd = new Element(id,"provider_account_credentials_hash_password")
    public Element providerQuota = new Element(id,"quota_instances")
    public Element providerAccountNumber = new Element(id,"provider_account_credentials_hash_account_id")
    public Element cloudAccountX509Private = new Element(id,"provider_account_credentials_hash_x509private")
    public Element cloudAccountX509Public = new Element(id,"provider_account_credentials_hash_x509public")
    //public Element addProviderAccount = new Element(id_button,"details","Add");
    //public Element removeProviderAccount = new Element(id_link,"details","Remove");
    //public Element AddAccount = new Element(link ,"Add");
    public Element deleteProviderAccount = new Element(link,"Delete")
    //public Element deleteButton = new Element("//input[@type='submit' and @value='delete']");
    public Element editButton = new Element("//input[@type='submit' and @value='Edit']")
    //public Element genericProviderNameLink   = new Element("//div[@id='providers_nav']/dl/dd/a");

    //Templates
    //public Element TemplatesList = new Element(table,"list of templates");
    //public Element TemplateCheckBox = new Element(checkboxNextToText, "test");
    public Element templateSelectedPackage = new Element(link_class, "packagename")


    //Template Creation Page
    public Element templateName = new Element(id,"tpl_name")
    public Element templateDescription = new Element(id,"tpl_summary")
    public Element platformChoice = new Element(id,"tpl_platform")
    public Element platformChoiceVersion = new Element(id,"tpl_platform_version")
    public Element platformArch = new Element(id,"tpl_architecture")
    public Element templateGroup 	= new Element(id,"template_group")
    public Element save = new Element(button,"Save")

    //public Element tempAction_Delete = new Element(icon,"delete");
    //public Element Edit = new Element(icon,"edit");
    public Element tempAction_Build = new Element(link,"Builds")
    public Element tempAction_Template = new Element(icon,"new_template")
    public Element tempAction_Assembly = new Element(icon,"assembly")
    public Element tempAction_DeploymentDef = new Element(icon,"deployment_definition")
    // Groups and packages
    public Element templateAddSoftware   = new Element(id,"add_software_button")
    public Element templateAddSelected = new Element(id,"do_add_software")
    public Element packageGroup_base = new Element(id,"group_base")
    public Element noSelectedPackages = new Element(divWithMessage,"grid_16 alpha omega","No selected packages")
    public Element collections = new Element(id,"collections")
    public Element searchTxtBox = new Element(id,"package_search")
    public Element searchButton = new Element(id,"package_search_button")
    public Element noPackagesFound = new Element(divWithMessage,"pageinfo","No packages found")
    public Element searchResults = new Element("//div[@id='metagrouppackages']/h4")
    public Element addingPackagesNotification = new Element("//span[@id='do_add_software' and @class='loading fl']")

    //Templates
    //public Element create = new Element(value,"Create");

    //Template Build Page
    public Element buildTarget = new Element(id,"target")
    public Element buildGO = new Element(value,"Go")

    //Instance Page
    public Element imageWareHouseStatus_green = new Element(div_span, "grid_3 suffix_6 omega", "good")
    public Element imageWareHouseStatus_red = new Element(div_span, "grid_3 suffix_6 omega", "bad") //guessing


    //Instance Management
    public Element launchInstance = new Element(link,"Launch Instance")
    public Element instanceName = new Element(id,"instance_name")
    public Element instanceHardwareProfile = new Element(id,"instance_hardware_profile_id")
    public Element instanceRealm = new Element(id,"instance_realm_id")
    public Element launchInstanceCancell = new Element(button,"Cancel")
    public Element launchInstanceLaunch = new Element(button,"Launch")
    public Element quotaExceeded = new Element(notification,"Quota Exceeded: Instance will not start until you have free quota")

    //Instance Action
    //public Element instanceControl_Shutdown = new Element(icon,"shutdown");
    public Element instanceControl_Details = new Element(icon,"instance_details")
    //public Element instanceControl_Failed = new Element(icon,"remove_failed");
    public Element back = new Element(button,"Back")

    //pools and Zones
    public Element editPools = new Element(button,"edit")
    //public Element deletePools = new Element(button,"delete");
    public Element newPools = new Element(button,"New Pool")






    // ADDING 1.1 ELEMENTS

    public Element createUser = new Element(id,"add_user_button")
    //public Element manageProviders = new TabElement(notSelectedNavLink, selectedNavLink,"Providers");
    //public Element genericProviderNameLink   = new Element("//table[@id='providers_table']/tbody/input");
    public Element Templates = new TabElement(notSelectedNavLink, selectedNavLink,"Templates")
    public Element genericProviderNameLink   = new Element("//table[@id='providers_table']//input/following-sibling::a")
    public Element addProvider = new Element(link, "Create")
    public Element addProviderSubmit = new Element(id,"provider_submit")
    public Element saveProviderSubmit = new Element(id,"provider_submit")
    public Element editProvider = new Element(link, "Edit")
    //public Element AddAccount = new Element(link ,"New Account");
    //public Element deleteButton = new Element(id, "delete_button");
    public Element removeProviderAccount = new Element(value, "Delete")
    public Element manageUsers = new TabElement(notSelectedNavLink, selectedNavLink,"Users")
    public Element tempAction_Delete = new Element(value,"Delete")
    public Element create = new Element(link,"Create")
    public Element Edit = new Element(link,"Edit")
    public Element deleteUser = new Element(value,"Delete")

    public Element deletePools = new Element(button,"Destroy")
    public Element InstanceManagement = new TabElement(notSelectedNavLink, selectedNavLink,"Instances")
    public Element instanceControl_Shutdown = new Element(id,"stop_selected_instances")
    public Element instanceControl_Failed = new Element(value,"Remove failed")

    public LocatorStrategy providerAccountCheckbox = new LocatorTemplate("providerAccountCheckbox", '''//a[normalize-space(.)='$1']/preceding-sibling::input[@type='checkbox']''')
    public LocatorStrategy providerAccountNameLink = new LocatorTemplate("providerAccountNameLink", '''//a[normalize-space(.)='$1']''')


    public Element resourceManagement = new Element(link,"Resource Management")
    public Element genericProAcctCheckbox = new Element(name, "accounts_selected[]")
    public Element selectAll = new Element(link, "All")
    public Element selectAllCheckBox = new Element(id, "select_all")
    public Element checkAll = new Element(id, "select_all")


    public Element image = new Element(link,"Image")
    public Element editButton_link = new Element(link, "Edit")
    public Element cancelButton_link = new Element(link, "Cancel")
    public Element properties = new Element(link,"Properties")

    //Templates
    public Element templateBuilds = new Element(link,"Builds")
    public Element templateArch = new Element(link,"ARCH")

    //Pools
    public Element Pool = new Element(link,"Pool")
    public Element new_Pool = new Element(link,"New Pool")
    public Element edit_Pools = new Element(link,"Edit")
    public Element newPool = new Element(id,"pool_name")
    public Element pool_save = new Element(button,"Save")

    //Roles
    public LocatorStrategy roleNameLink = new LocatorTemplate("roleNameLink", '''//table[@id='roles_table']//a[normalize-space(.)='$1']''')
    public LocatorStrategy roleTargetAction = new LocatorTemplate("roleTargetAction", '''//tr/td[normalize-space(.)='$1']/following-sibling::td[normalize-space(.)='$2']''')
    public LocatorStrategy genericActionRoleTarget = new LocatorTemplate("genericActionRoleTarget", '''//tr/td/following-sibling::td[normalize-space(.)='$1']''')
    public LocatorStrategy roleScope = new LocatorTemplate("roleScope", '''//h2[normalize-space(.)='$1']/following-sibling::strong[normalize-space(.)='Scope:']/following-sibling::span''')
    public LocatorStrategy roleCheckbox = new LocatorTemplate("roleCheckbox", '''//a[normalize-space(.)='$1']/preceding-sibling::input[@type='checkbox']''')
    public LocatorStrategy getRoleActionTargetByRowIndex = new LocatorTemplate("getRoleActionTargetByRowIndex", '''//tr[$1]/td/following-sibling::td''')
    public Element genericRoleNameLink = new Element("//table[@id='roles_table']//a")
    public String roleRow_string = "//table[@id='roles_table']//input[@name='role_selected[]']"
    public Element createNewRole = new Element(link, "New Role")
    public Element deleteRole = new Element(id, "delete_button")
    public Element roleNameField = new Element(id, "role_name")
    public Element roleFormSave = new Element("//input[@value='Save']")
    public Element roleFormReset = new Element("//input[@value='Reset']")
    public Element genericRoleCheckbox = new Element(name, "role_selected[]")
    public Element genericActionGenericRoleTarget = new Element("//tr/td/following-sibling::td")

    //hardware profiles
    public String sHwpMainTableNameHeaderText = "Hardware Profile Name"
    public String sHwpDetailsTableNameHeaderText = "Name"
    public String sHwpProviderTableNameHeaderText = "Provider Name"
    public String sHwpMainContainerXpath = "//div[@id='list-view']"
    public String sHwpDetailsContainerXpath = "//div[@id='details-selected']"
    public LocatorStrategy lsHwpProviderHeading = new LocatorTemplate("lsHwpProviderHeading", '''//h3[normalize-space(.)='$1']''')
    public LocatorStrategy lsHwpProviderContainer = new LocatorTemplate("lsHwpProviderContainer", '''//div[@id='$1']''')
    public LocatorStrategy lsHwpGeneralHeading = new LocatorTemplate("hwpGeneralHeading", '''$1/h3''')
    public LocatorStrategy lsHwpGeneralTableRow = new LocatorTemplate("hwpTableRow", '''$1//table/tbody/tr''')
    public LocatorStrategy lsHwpGeneralTableHeaderCell = new LocatorTemplate("hwpTableHeaderCell", '''$1//table/thead/tr/th''')
    public LocatorStrategy lsHwpSpecificHeadingContains = new LocatorTemplate("hwpSpecificHeadingContains", '''$1/h3[contains(.,'$2')]''')		// comment
    public LocatorStrategy lsHwpSpecificTableHeaderCell = new LocatorTemplate("hwpTableHeaderCell", '''$1//table/thead/tr/th[$2]''')
    public LocatorStrategy lsHwpSpecificTableCell = new LocatorTemplate("hwpSpecificTableCell", '''$1//table/tbody/tr[$2]/td[$3]''')
    public Element eHwpProvidersButton = new Element(link, "Matching Provider Hardware Profiles")

    public Element hardwareProfile_new = new Element(id,"new_hardware_profile_button")
    public Element hardwareProfile_name = new Element(id,"hardware_profile_name")
    public Element hardwareProfile_memory = new Element(id,"hardware_profile_memory_attributes_value")
    public Element hardwareProfile_cpu = new Element(id,"hardware_profile_cpu_attributes_value")
    public Element hardwareProfile_storage = new Element(id,"hardware_profile_storage_attributes_value")
    public Element hardwareProfile_architecture = new Element(id,"hardware_profile_architecture_attributes_value")
    public Element hardwareProfile_checkMatches = new Element(value,"Check Matches")
    public Element hardwareProfile_save = new Element(value,"Save")

    public Element eHwpCreateButton = new Element(link, "New Hardware Profile")
    public Element eHwpCreateNameField = new Element(id, "hardware_profile_name")
    public Element eHwpCreateMemoryField = new Element(id, "hardware_profile_memory_attributes_value")
    public Element eHwpCreateCpuField = new Element(id, "hardware_profile_cpu_attributes_value")
    public Element eHwpCreateStorageField = new Element(id, "hardware_profile_storage_attributes_value")
    public Element eHwpCreateArchField = new Element(id, "hardware_profile_architecture_attributes_value")
    public Element eHwpCheckMatches = new Element(value, "Check Matches")
    public Element eHwpSaveButton = new Element(value, "Save")

    // search box
    public Element eSearchTextField = new Element(id, "q")
    public Element eSearchSubmit = new Element(value, "Search")

    // providers
    public Element eProviderTypeSelectBox = new Element(id, "provider_provider_type_id")


    //instances
    public Element createSubmit = new Element(value,"Create")
    public Element instancePoolSelect = new Element(id,"instance_pool_id")

    //Image Import
    public Element ImageId = new Element(id,"ami_id")
    public Element Import = new Element(value,"Import")
    public Element ProviderAccountID = new Element(id,"provider_account_id")

    //Generic
    public Element checkBoxBlank = new Element("//input[@Type='checkbox']")

    // End Adding 1.1 Elements


    // Begin Adding 1.2 Elements


    public Element  deleteButton = new Element(id,"delete_button")
    public Element saveButton = new Element(id,"save")
    public Element notificaton = new Element("//img[@alt='Notices']")
    public Element Errors = new Element(imgAlt,"Errors")
    //note to check error/notification message
    //public LocatorStrategy notificationMessage = new LocatorTemplate("notification","//li[normalize-space(.)=$1");


    //NAVIGATION
    //public TabElement administer = new TabElement(notSelectedNavLink, selectedNavLink,"Administer");
    public Element administer = new Element(link,"Administer")
    public Element administerSelected = new Element("//a[@Class='selected' and normalize-space(.)='Administer']")
    public Element designDeploy = new Element(href,"/conductor/legacy_templates")

    //Administer
    public Element cloudProviders = new Element(link,"Cloud Resource Providers")
    public Element cloudProvidersUpstream = new Element(link,"Cloud Providers")
    public Element cloudProvidersChooseAProvider = new Element("//span[@Class='label light' and normalize-space(.)='Choose a provider:']")
    public Element usersAndGroups = new Element("//nav[@id='administer_nav']//a[@href='/conductor/users']")
    public Element usersHeader = new Element("//h2[@Class='users']")
    public Element rolesHeader = new Element("//h1[@Class='roles']")
    public Element providersHeader = new Element("//h1[@Class='providers']")
    public Element providerAccountHeader = new Element("//h1[@Class='provider_accounts']")
    public Element hardwareProfileHeader = new Element("//h1[@Class='hardware_profiles']")
    public Element realmsHeader = new Element("//h1[@Class='realms']")
    public Element poolFamiliesHeader = new Element("//h1[@Class='pool_families']")
    public Element settingsHeader = new Element("//h1[@Class='settings']")
    public Element catalogEntriesHeader = new Element("//h1[@Class='catalog_entries']")
    public Element catalogHeader = new Element("//h1[@Class='catalogs']")
    public Element content = new Element(link,"Content")
    public Element hardware = new Element(id,"details_hardware_profiles")
    public Element realms = new Element(id_alt,"details_realms")

    //Providers and Providers Accounts
    public Element providerAccountNew = new Element(id,"new_provider_account")
    public Element manageProviders = new TabElement(notSelectedNavLink, selectedNavLink,"Providers")
    public Element providerAccountsList = new Element(id_alt,"details_accounts")
    public Element providerAccountsListLink = new Element(link,"Accounts")



    //Realms
    public Element realmNew = new Element(id,"new_realm_button")
    public Element realmName = new Element(id,"frontend_realm_name")
    public Element realmAddMappingToRealm = new Element(id,"mapping_to_realm_button")
    public Element realmAddMappingToProvider = new Element(id,"mapping_to_provider_button")
    public Element realmMapping = new Element(select_id,"realm_backend_target_realm_or_provider_id")

    // Catalog Entry List

    public Element DeployableNew = new Element(id,"new_deployable_button")



    public Element catalogEntryName = new Element(id_alt,"deployable_name")
    public Element catalogEntryFromURL = new Element(id_alt,"from_url")
    public Element catalogEntryUpload = new Element(id_alt,"upload")
    public Element catalogEditXML = new Element(id_alt,"edit_xml")
    public Element catalogEntryDescription = new Element(id_alt,"deployable_description")
    public Element catalogEntryURL = new Element(id_alt,"url")

    public Element catalogEntryID = new Element(id,"catalog_entry_catalog_id")

    //Catalogs
    public Element catalogs = new Element(id,"details_catalogs")
    public Element catalogNew = new Element(id,"new_catalog_button")
    public Element catalogName = new Element(id,"catalog_name")
    public Element catalogPool = new Element(id,"catalog_pool_id")


    //Monitor
    public Element monitorTab = new Element(link,"Monitor")
    public Element defaultPool = new Element(link,"Default")
    public Element filterView = new Element(id,"filter_view")
    public Element instancesList = new Element(id,"details_instances")
    public Element instanceStopSelected = new Element(id,"stop_selected_instances")



    //Deployments
    public Element deploymentNew = new Element(id,"new_deployment_button")
    public Element deploymentName = new Element(id,"deployment_name")
    public Element deploymentDefinition = new Element(id,"deployable_id")
    public Element deploymentRealm = new Element(id,"deployment_frontend_realm_id")
    public Element Next = new Element(value,"Next")
    public Element deploymentLaunch = new Element(id,"launch_deployment_button")


    //Environments and Pools
    public Element clouds = new Element(link,"Clouds")

    //Config Server
    public Element configServerAdd = new Element(link,"Add")
    public Element configServerEndpoint = new Element(id,"config_server_endpoint")
    //public Element configServerPort = new Element(id,"config_server_port");
    public Element configServerConsumerKey = new Element(id,"config_server_key")
    public Element configServerConsumerSecret = new Element(id,"config_server_secret")
    public Element configServerCertificate = new Element(id,"config_server_certificate")

    public Element configLaunchTimeHeader = new Element("//h2[normalize-space(.)='Configure launch-time parameters for your application:']")
    public Element configFinalize = new Element(id,"submit_params")
    public Element configServerTest = new Element(link,"Test")









}
