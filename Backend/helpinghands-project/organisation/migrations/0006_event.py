# Generated by Django 2.0.2 on 2018-10-16 12:39

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('organisation', '0005_auto_20181016_1148'),
    ]

    operations = [
        migrations.CreateModel(
            name='Event',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=255)),
                ('contact_number', models.TextField(max_length=255)),
                ('email', models.CharField(max_length=255)),
                ('address', models.TextField(max_length=255)),
                ('date', models.TextField(max_length=255)),
                ('time', models.TextField(max_length=255)),
                ('total_volunteers', models.IntegerField(max_length=255)),
                ('volunteers_volunteered', models.IntegerField(default=0, max_length=255)),
                ('volunteers_left', models.IntegerField(default=0, max_length=255)),
                ('number_of_people', models.IntegerField(max_length=255)),
                ('is_pickup_available', models.BooleanField(default=False)),
                ('category', models.CharField(max_length=255)),
                ('is_completed', models.BooleanField(default=False)),
            ],
        ),
    ]