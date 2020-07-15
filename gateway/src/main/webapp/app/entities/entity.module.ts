import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'speaker',
        loadChildren: () => import('./conference/speaker/speaker.module').then(m => m.ConferenceSpeakerModule),
      },
      {
        path: 'session',
        loadChildren: () => import('./conference/session/session.module').then(m => m.ConferenceSessionModule),
      },
      {
        path: 'author',
        loadChildren: () => import('./blog/author/author.module').then(m => m.BlogAuthorModule),
      },
      {
        path: 'blogpost',
        loadChildren: () => import('./blog/blogpost/blogpost.module').then(m => m.BlogBlogpostModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GatewayEntityModule {}
