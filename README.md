## What is this library?
A small UI library built with Jetpack compose that contains the NeoPOP design style(3D) components.

## Components

| NeoPopButton Primary | NeoPop3DPressableContent | NeoPopBottomSheetContainer |
| ------------- | ------------- | ------------- |
| <img src="https://github.com/user-attachments/assets/1c941865-7e60-42c2-b267-2f146bee0d10" alt="NeoPopButton" width="300"/> | ![NeoPop3DPressableContent](https://github.com/user-attachments/assets/486fa31f-7a46-44ed-84a8-6360d8e37ae9) |  ![NeoPopBottomSheetContainer](https://github.com/user-attachments/assets/1c0a7210-c357-47bd-868b-51adc1219f7e) |

| NeoPopButtonRotatedX |
| ------------- |
| <img src="https://github.com/user-attachments/assets/b7462fc7-e6a8-4d04-8774-0edf137689ee" alt="NeoPopButtonRotatedX" width="100%"/> | 

| NeoPopButtonRotatedXShadowed |
| ------------- |
| <img src="https://github.com/user-attachments/assets/a25ae9c1-1e9b-40c2-81a4-1f3627fb407b" alt="NeoPopButtonRotatedX" width="100%"/> | 


## How to Install?
1. Open your Project's settings.gradle file.
2. Add the following...
```
dependencyResolutionManagement {
    ...
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io") // Add this
        }
    }
}
```
3. Go to build.gradle of app module
4. Add the following dependency:
```
dependencies {
    ...
    implementation("com.github.Sagar0-0:neopop-compose:1.0.0")// Add this with latest version
}
```
5. Enjoy using NeoPOP components

## Example Usage:
```
Row {
                NeoPopButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    content = {
                        Text(
                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                            text = "Pay Now",
                            textAlign = TextAlign.Center,
                            style = CustomTheme.typography.bodySemibold.copy(color = CustomTheme.colors.textWhite)
                        )
                    }
                )
                NeoPopButton(
                    onClick = {},
                    content = {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                painter = painterResource(id = R.drawable.type_premium_offer),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Text(
                                text = "Pay later",
                                style = CustomTheme.typography.bodySemibold.copy(color = CustomTheme.colors.textWhite),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                )
            }
```
### Output:
![image](https://github.com/user-attachments/assets/95922909-ca26-4724-a0d0-64c017a0bce3)
