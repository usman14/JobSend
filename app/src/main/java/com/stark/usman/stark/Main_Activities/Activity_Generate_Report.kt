package com.stark.usman.stark.Main_Activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.stark.usman.stark.Realm_Objects.Realm_Project_unit
import com.stark.usman.stark.Realm_Objects.Realm_User
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


open class Activity_Generate_Report: Activity() {
    lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
        try {
            realm = Realm.getDefaultInstance()
        } catch (e: Exception) {
            val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm = Realm.getInstance(config)
        }

    }


    open fun createandDisplayPdf(text: String) {
        Toast.makeText(this, "pdf called", Toast.LENGTH_LONG).show()

        val doc = Document()
        val path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Stark.pdf"

        try {
            PdfWriter.getInstance(doc, FileOutputStream(path))
            doc.open()

            /**val dir = File(path)
            if (!dir.exists())
            dir.mkdirs()

            val file = File(dir, "newFile.pdf")
            val fOut = FileOutputStream(file)


            //open the document
            doc.open()*/
            val list = realm.where(Realm_Project_unit::class.java).findAll()
            for (i in 0..list.size - 1) {
                val p1 = Paragraph(list[i].project_name + "  " + list[i].start_time + "  " + list[i].finish_time)
                //doc.add(p1)
            }

            val p1 = Paragraph(text)
            // val paraFont = Font(Font.BOLD)
            p1.setAlignment(Paragraph.ALIGN_CENTER)
            //p1.setFont(paraFont)

            //add paragraph to document
            doc.add(p1)


        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } catch (e: IOException) {
            Log.e("PDFCreator", "ioException:$e")
        } finally {
            doc.close()
        }

        viewPdf("newFile.pdf", "Dir")
    }

    // Method for opening a pdf file
    private fun viewPdf(file: String, directory: String) {

        val pdfFile = File(getExternalStorageDirectory().toString() + "/Stark.pdf")
        val path = Uri.fromFile(pdfFile)

        // Setting the intent for pdf reader
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.setDataAndType(path, "application/pdf")
        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        try {
            startActivity(pdfIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this@Activity_Generate_Report, "Can't read pdf file", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            0 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults != null) {
                    if ((grantResults.size > 0 && grantResults!![0] === PackageManager.PERMISSION_GRANTED && grantResults!![1] === PackageManager.PERMISSION_GRANTED)) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        //createandDisplayPdf("usman")
                        createPdf()

                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        Toast.makeText(this@Activity_Generate_Report, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show()
                    }
                }
                return
            }
        }
    }

    @Throws(IOException::class, DocumentException::class)
    fun createPdf() {
        val listone = realm.where(Realm_User::class.java).findAll()
        val count = realm.where(Realm_User::class.java).count()
        if(count>0)
        {
            Toast.makeText(this,"Creating Pdf File",Toast.LENGTH_LONG).show()
            val month_name=intent.getStringExtra("month")

            val list = realm.where(Realm_Project_unit::class.java).equalTo("month",month_name).findAllSorted("monthid",Sort.DESCENDING)

            val document = Document()
            val path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Workreport"
            val dir = File(path)
            if (!dir.exists())
                dir.mkdirs()

            val file = File(dir, list[0]?.month)
            val fOut = FileOutputStream(file)


            //open the document

            Log.d("listsize",list.size.toString())
            PdfWriter.getInstance(document, fOut)
            document.open()
            var p5=Paragraph("Company Name :   "+listone[0]?.company_name)
            var p1=Paragraph("Worker Name  :   "+listone[0]?.worker_name)
            var p2=Paragraph("Worker Id    :   "+listone[0]?.worker_id)
            var p3=Paragraph("Report Month :   "+list[0]?.month)
            var p4=Paragraph("Manager Name :   "+listone[0]?.manager_name)

            p1.setAlignment(Paragraph.ALIGN_CENTER)
            p2.setAlignment(Paragraph.ALIGN_CENTER)
            p3.setAlignment(Paragraph.ALIGN_CENTER)
            p4.setAlignment(Paragraph.ALIGN_CENTER)
            p5.setAlignment(Paragraph.ALIGN_CENTER)

            p1.font.isBold
            p2.font.isBold
            p3.font.isBold
            p4.font.isBold
            p5.font.isBold
            document.add(p5)
            document.add(p1)
            document.add(p2)
            document.add(p3)
            document.add(p4)



            //var table = PdfPTable(6)
            val table = PdfPTable(floatArrayOf(1f,1f, 1f,1f,1f,1f,2f))
            table.setWidthPercentage(100f);

            table.setSpacingBefore(10F)
            table.setSpacingAfter(10F)
            table.addCell("PROJECT NAME")
            table.addCell("PROJECT ADDRESS")
            table.addCell("PROJECT DATE")
            table.addCell("STARTING TIME")
            table.addCell("FINISH TIME")
            table.addCell("WORK HOURS")
            table.addCell("REMARKS")
            for (i in 0..list.size-1) {


                table.addCell(list[i].project_name)
                table.addCell(list[i].project_address)
                table.addCell(list[i].project_date)
                table.addCell(list[i].start_time)
                table.addCell(list[i].finish_time)
                table.addCell(list[i].workhours)
                table.addCell(list[i].remarks)


            }
            document.add(table)
            document.close()
            viewPdf1(list[0].month.toString())
        }
        else
        {
            Toast.makeText(this@Activity_Generate_Report, "Please fill your personal data before generating report.", Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }
    private fun viewPdf1(file: String) {

        val pdfFile = File(getExternalStorageDirectory().toString() + "/Workreport"+"/"+file)
        val path = Uri.fromFile(pdfFile)

        // Setting the intent for pdf reader
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.setDataAndType(path, "application/pdf")
        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP


        try {
            startActivity(pdfIntent)
            this.finish()
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this@Activity_Generate_Report, "Can't read pdf file", Toast.LENGTH_SHORT).show()
        }

    }

}
