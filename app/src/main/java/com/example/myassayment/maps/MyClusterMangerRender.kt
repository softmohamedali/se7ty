package com.example.myassayment.maps

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import coil.ImageLoader
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.LayoutMarkerItemBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import java.util.concurrent.atomic.AtomicInteger
import android.graphics.Bitmap
import androidx.test.core.app.ApplicationProvider
import coil.Coil
import com.bumptech.glide.request.animation.GlideAnimation

import com.bumptech.glide.request.target.SimpleTarget

import androidx.test.core.app.ApplicationProvider.getApplicationContext

import com.bumptech.glide.Glide

class MyClusterMangerRender(
    var context: Activity,
    map: GoogleMap,
    clusterManger: ClusterManager<ClusterMarker>
) : DefaultClusterRenderer<ClusterMarker>(context, map, clusterManger) {
    var iconGenerator:IconGenerator
    var img:ImageView
    val markerWidth:Int=200
    val markerheight:Int=200
    var imgDowloadCounter:AtomicInteger

    init {
        imgDowloadCounter= AtomicInteger(0)
        iconGenerator= IconGenerator(context)
        val clusterView=LayoutMarkerItemBinding.inflate(LayoutInflater.from(context),null,false)
        img=clusterView.imageView8
        iconGenerator.setContentView(clusterView.root)
        iconGenerator.setBackground(null)
    }


    override fun onClusterItemRendered(clusterItem: ClusterMarker, marker: Marker) {
        super.onClusterItemRendered(clusterItem, marker)
        Glide.with(context).load(clusterItem.doctor.photo).into(img)
    }
    override fun onBeforeClusterItemRendered(item: ClusterMarker, markerOptions: MarkerOptions) {
        initImageSizeIfNeed()
        val icon=iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title).snippet(item.snippet)
        icon.recycle()
    }

    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarker>): Boolean {
        return false
    }

    private fun initImageSizeIfNeed() {

    }

}











//class MyClusterMangerRender(
//    var context: Activity,
//    map: GoogleMap,
//    clusterManger: ClusterManager<ClusterMarker>
//) : DefaultClusterRenderer<ClusterMarker>(context, map, clusterManger) {
//    lateinit var iconGenerator: IconGenerator
//    lateinit var imgView:ImageView
//    lateinit var clusterGenertor:IconGenerator
//    var img:Bitmap
//    var mask:Bitmap
//    var imgDowloadCounter:AtomicInteger
//
//    init {
//        imgDowloadCounter= AtomicInteger(0)
//        mask=BitmapFactory.decodeResource(context.resources,R.drawable.img100)
//        img=BitmapFactory.decodeResource(context.resources,R.drawable.img100)
//        setUpClusterIcon()
//        setUpMarker()
//
//    }
//
//    private fun setUpMarker() {
//        iconGenerator= IconGenerator(context)
//        val clusterView=LayoutMarkerItemBinding.inflate(LayoutInflater.from(context),null,false)
//        imgView=clusterView.imageView8
//        iconGenerator.setContentView(clusterView.root)
//        iconGenerator.setBackground(null)
//    }
//
//    private fun setUpClusterIcon() {
//        this.clusterGenertor=IconGenerator(context)
//        //        this.imgView= ImageView(context.applicationContext).apply {
////            layoutParams= ViewGroup.LayoutParams(120,120)
////            this.setPadding(5,5,5,5)
////        }
////        this.imgView2= ImageView(context.applicationContext).apply {
////            layoutParams= ViewGroup.LayoutParams(120,120)
////            this.setPadding(5,5,5,5)
////        }
//    }
//
//    override fun onClusterItemRendered(clusterItem: ClusterMarker, marker: Marker) {
//        super.onClusterItemRendered(clusterItem, marker)
//        imgView.load(clusterItem.img)
//    }
//
//    override fun onClusterRendered(cluster: Cluster<ClusterMarker>, marker: Marker) {
//        super.onClusterRendered(cluster, marker)
//        val list= ArrayList(cluster.items)
//        val urlFirstImage=list[0].img
//        imgView.load(urlFirstImage)
//    }
//
//
//    override fun onBeforeClusterItemRendered(item: ClusterMarker, markerOptions: MarkerOptions) {
//        initImageSizeIfNeed()
//        val icon=iconGenerator.makeIcon()
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title)
//        icon.recycle()
//    }
//
//    override fun onBeforeClusterRendered(
//        cluster: Cluster<ClusterMarker>,
//        markerOptions: MarkerOptions
//    ) {
//        initImageSizeIfNeed()
//        val icon=iconGenerator.makeIcon()
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
//        icon.recycle()
//    }
//
//    private fun initImageSizeIfNeed() {
//
//    }
//
//    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarker>): Boolean {
//        return cluster.size>1
//    }
//
//    override fun onClusterItemUpdated(item: ClusterMarker, marker: Marker) {
//        marker.title="hey there"
//    }
//
//    override fun setOnClusterItemClickListener(listener: ClusterManager.OnClusterItemClickListener<ClusterMarker>?) {
//        super.setOnClusterItemClickListener(listener)
//        Log.d("mylog","hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiihhhhhhhhlllllloooooooooo")
//    }
//}




//class MyClusterMangerRender(
//    var context: Activity,
//    map: GoogleMap,
//    clusterManger: ClusterManager<ClusterMarker>
//) : DefaultClusterRenderer<ClusterMarker>(context, map, clusterManger) {
//    lateinit var iconGenerator: IconGenerator
//    lateinit var imgView:ImageView
//
//    init {
//
//        setUpMarker()
//
//    }
//
//    private fun setUpMarker() {
//        iconGenerator= IconGenerator(context)
//        val clusterView=LayoutMarkerItemBinding.inflate(LayoutInflater.from(context),null,false)
//        imgView=clusterView.imageView8
//        iconGenerator.setContentView(clusterView.root)
//        iconGenerator.setBackground(null)
//    }
//
//
//
//    override fun onClusterItemRendered(clusterItem: ClusterMarker, marker: Marker) {
//        super.onClusterItemRendered(clusterItem, marker)
//        imgView.load(clusterItem.img)
//    }
//
//
//
//
//    override fun onBeforeClusterItemRendered(item: ClusterMarker, markerOptions: MarkerOptions) {
//        initImageSizeIfNeed()
//        val icon=iconGenerator.makeIcon()
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title)
//        icon.recycle()
//    }
//
//
//
//    private fun initImageSizeIfNeed() {
//
//    }
//
//    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarker>): Boolean {
//        return false
//    }
//    override fun onClusterItemUpdated(item: ClusterMarker, marker: Marker) {
//        marker.title="hey there"
//    }
//
//    override fun setOnClusterItemClickListener(listener: ClusterManager.OnClusterItemClickListener<ClusterMarker>?) {
//        super.setOnClusterItemClickListener(listener)
//        Log.d("mylog","hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiihhhhhhhhlllllloooooooooo")
//    }
//}



//class MyClusterMangerRender(
//    var context: Activity,
//    map: GoogleMap,
//    clusterManger: ClusterManager<ClusterMarker>
//) : DefaultClusterRenderer<ClusterMarker>(context, map, clusterManger) {
//    var iconGenerator:IconGenerator
//    var img:ImageView
//    val markerWidth:Int=200
//    val markerheight:Int=200
//    var imgDowloadCounter:AtomicInteger
//
//    init {
//        imgDowloadCounter= AtomicInteger(0)
//        iconGenerator= IconGenerator(context)
//        img=ImageView(context)
//        img.layoutParams=ViewGroup.LayoutParams(markerWidth,markerheight)
//        img.setPadding(10,10,10,10)
//        iconGenerator.setContentView(img)
//    }
//
//    override fun onBeforeClusterItemRendered(item: ClusterMarker, markerOptions: MarkerOptions) {
//        img.setImageResource(item.img)
//        val icon=iconGenerator.makeIcon()
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title)
//    }
//    private fun setUpMarker() {
//        iconGenerator= IconGenerator(context)
//        val clusterView=LayoutMarkerItemBinding.inflate(LayoutInflater.from(context),null,false)
//        img=clusterView.imageView8
//        iconGenerator.setContentView(clusterView.root)
//        iconGenerator.setBackground(null)
//    }
//    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarker>): Boolean {
//        return false
//    }
//}
