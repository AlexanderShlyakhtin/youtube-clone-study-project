import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UploadVideoComponent} from './upload-video/upload-video.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NgxFileDropModule} from "ngx-file-drop";
import {AppRoutingModule} from "./app-routing.module";
import {HeaderComponent} from './header/header.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {SaveVideoDetailsComponent} from './save-video-details/save-video-details.component';
import {MatButtonModule} from "@angular/material/button";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatLineModule, MatOptionModule} from "@angular/material/core";
import {MatInputModule} from "@angular/material/input";
import {MatChipsModule} from "@angular/material/chips";
import {VgCoreModule} from "@videogular/ngx-videogular/core";
import {VgControlsModule} from "@videogular/ngx-videogular/controls";
import {VgOverlayPlayModule} from "@videogular/ngx-videogular/overlay-play";
import {VgBufferingModule} from "@videogular/ngx-videogular/buffering";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {VideoPlayerComponent} from './video-player/video-player.component';
import {AuthConfigModule} from './auth/auth-config.module';
import {AuthInterceptor} from "angular-auth-oidc-client";
import { VideoDetailComponent } from './video-detail/video-detail.component';
import { HomeComponent } from './home/home.component';
import { HistoryComponent } from './history/history.component';
import { SubscriptionComponent } from './subscription/subscription.component';
import { LikedVideoComponent } from './liked-video/liked-video.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import { FeaturedComponent } from './featured/featured.component';
import { VideoCardComponent } from './video-card/video-card.component';
import {MatCardModule} from "@angular/material/card";
import { CallbackComponent } from './callback/callback.component';
import { CommentsComponent } from './comments/comments.component';
import {MatMenuModule} from "@angular/material/menu";

@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    HeaderComponent,
    SaveVideoDetailsComponent,
    VideoPlayerComponent,
    VideoDetailComponent,
    HomeComponent,
    HistoryComponent,
    SubscriptionComponent,
    LikedVideoComponent,
    SidebarComponent,
    FeaturedComponent,
    VideoCardComponent,
    CallbackComponent,
    CommentsComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientModule,
        NgxFileDropModule,
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        FlexLayoutModule,
        MatFormFieldModule,
        MatSelectModule,
        MatOptionModule,
        MatInputModule,
        ReactiveFormsModule,
        MatChipsModule,
        VgCoreModule,
        VgControlsModule,
        VgOverlayPlayModule,
        VgBufferingModule,
        MatSnackBarModule,
        AuthConfigModule,
        MatSidenavModule,
        MatListModule,
        MatLineModule,
        MatCardModule,
        MatMenuModule
    ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  exports: [
    HeaderComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
