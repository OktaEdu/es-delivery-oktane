import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Error from '@/components/Error'
import Promo from '@/components/Promo'
import Profile from '@/components/Profile'
import { retrieveTokensRedirect, tokenCallback, validateAccess, retrieveTokensXHR, logout, setState } from '../auth'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    //Public pages
    { path: '*', redirect: '/home' }, //redirect to make sure you land in a page
    { path: '/home', component: Home }, //home page
    { path: '/error', component: Error },
    //Private pages (displayed only user access is validated)
    { path: '/premium-promos', beforeEnter: validateAccess, component: Promo },
    { path: '/profile', beforeEnter: validateAccess, component: Profile },
    //Functions without page
    { path: '/retrieveTokensRedirect', component: retrieveTokensRedirect },
    { path: '/retrieveTokensXHR', beforeEnter: setState, component: retrieveTokensXHR },
    { path: '/tokenCallback', component: tokenCallback },
    { path: '/logout', component: logout },
  ]
})
