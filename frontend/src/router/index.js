import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/views/Layout";

const routes = [
  {
    path: '/',
    redirect: '/problemset',
    component: Layout,
    children: [
      {
        path: '/problemset',
        component: ()=>import('../views/Problem/ProblemSet'),
      },
      {
        path: '/contest',
        component: ()=>import('../views/Contest/ContestList'),
      },
      {
        path: '/contest/:id',
        component: ()=>import('../views/Contest/ContestDetail'),
        redirect: '',
        children: [
          {
            path: '',
            component: ()=>import('../views/Contest/ContestOverview')
          },
          {
            path: '/contest/:id/announcements',
            component: ()=>import('../views/Contest/ContestAnnouncements')
          },
          {
            path: '/contest/:id/problems',
            component: ()=>import('../views/Contest/ContestProblems')
          },
          {
            path: '/contest/:id/problems/:displayId',
            component: ()=>import('../views/Contest/ContestProblemDetail'),
            children:[
              {
                path: '',
                component: ()=>import('../views/Problem/ProblemDescription'),
              },
              {
                path: 'submitRecord',
                component: ()=>import('../views/Problem/ProblemSubmitRecord'),
              }
            ]
          },
          {
            path: '/contest/:id/submissions',
            component: ()=>import('../views/Contest/ContestSubmissions')
          },
          {
            path: '/contest/:id/rankings',
            component: ()=>import('../views/Contest/ContestRankings')
          }
        ]
      },
      {
        path: '/forum',
        component: ()=>import('../views/Forum/Forum'),
      },
      {
        path: '/test',
        component: ()=>import('../views/Test'),
      },
      {
        path: '/admin',
        component: ()=>import('../views/Admin/Admin'),
        redirect: '',
        children: [
          {
            path: '',
            component: ()=>import('../views/Admin/Dashboard'),
          },
          {
            path: 'problems',
            component: ()=>import('../views/Problem/ProblemList'),
          },
          {
            path: 'problem/create',
            component: ()=>import('../views/Problem/ProblemCreate'),
          }
        ]
      },
      {
        path: '/problem/:id',
        name: 'ProblemDetail',
        redirect: '',
        component: ()=>import('../views/Problem/ProblemDetail'),
        children: [
          {
            path: '',
            name: 'ProblemDescription',
            component: ()=>import('../views/Problem/ProblemDescription')
          },
          {
            path: 'comments',
            name: 'ProblemComments',
            component: ()=>import('../views/Problem/ProblemComments')
          },
          {
            path: 'solutions',
            name: 'ProblemSolutions',
            component: ()=>import('../views/Problem/ProblemSolutions')
          },
          {
            path: 'submitRecord',
            name: 'ProblemSubmitRecord',
            component: ()=>import('../views/Problem/ProblemSubmitRecord')
          }
        ]
      },
      {
        path: '/home',
        component: ()=>import('../views/Home/Home')
      },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
