import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { BlogpostComponent } from './blogpost.component';
import { BlogpostDetailComponent } from './blogpost-detail.component';
import { BlogpostUpdateComponent } from './blogpost-update.component';
import { BlogpostDeleteDialogComponent } from './blogpost-delete-dialog.component';
import { blogpostRoute } from './blogpost.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(blogpostRoute)],
  declarations: [BlogpostComponent, BlogpostDetailComponent, BlogpostUpdateComponent, BlogpostDeleteDialogComponent],
  entryComponents: [BlogpostDeleteDialogComponent],
})
export class BlogBlogpostModule {}
