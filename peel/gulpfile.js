var gulp = require('gulp'),
    sass = require('gulp-sass'),
    minifycss = require('gulp-minify-css'),
    rename = require('gulp-rename'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat'),
    notify = require('gulp-notify'),
    ejs = require("gulp-ejs"),
    watch = require('gulp-watch'),
    browserSync = require('browser-sync').create(),
    del = require('del');

var catalog = '../tree/src/main/resources/static/';

gulp.task('styles', function() {
    return gulp.src('src/css/**/*.scss')
        .pipe(sass())
        .pipe(gulp.dest(catalog+'css'))
        .pipe(notify({ message: 'styles task complete' }));
});
gulp.task('js',function(){
    return gulp.src(['src/js/**/*.js'])
        .pipe(gulp.dest(catalog+'js'))
        .pipe(notify({ message: 'js task complete' }));
});
gulp.task('ejs', function () {
    return  gulp.src(["src/html/**/*.html"])
        .pipe(ejs({}))
        .pipe(gulp.dest(catalog + "html"))
        .pipe(notify({ message: 'html task complete' }));
});
gulp.task('watch', function() {
    gulp.watch('src/css/**/*.scss', ['styles']);
    gulp.watch('src/js/**/*.js', ['js']);
    gulp.watch('src/html/**/*.html', ['ejs']);
    gulp.watch('src/html/**/*.ejs', ['ejs']);
});
gulp.task('default', ['ejs','js','styles','watch']);
